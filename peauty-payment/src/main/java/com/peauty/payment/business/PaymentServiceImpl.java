package com.peauty.payment.business;

import com.peauty.domain.payment.Order;
import com.peauty.domain.payment.Payment;
import com.peauty.payment.business.dto.CompletePaymentCallbackCommand;
import com.peauty.payment.business.dto.CompletePaymentCallbackResult;
import com.peauty.payment.business.dto.OrderCommand;
import com.peauty.payment.business.dto.OrderResult;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentPort paymentPort;
    private final IamportClient iamportClient;

    // TODO. 1. 사전 검증
    // TODO: 2. 사후 검증
    //      - 포트원에서 결제 정보 가져오기

    @Transactional
    @Override
    public OrderResult saveOrder(Long userId, Long processId, Long threadId, OrderCommand command) {
        // TODO: 유저 검증
        // TODO: 요청서 & 견적서 정보 검증 (요청서, 견적서가 DB에 있는지 검증)
        String uuid = UUID.randomUUID().toString();
        LocalDateTime orderDate = LocalDateTime.now();
        Payment paymentToSave = Payment.initializePayment(userId, command);

        Order orderToSave = command.toDomain(
                userId, processId, threadId,
                uuid, orderDate, paymentToSave);

        // 예약금 변환을 thread에서 가져와서 변환을 해야함 -> 그 금액과 같은지 확인해야 함
        orderToSave.transferReservationCost(orderToSave.getPrice());
        Order savedOrder = paymentPort.saveOrder(orderToSave);
        return OrderResult.from(savedOrder);
    }

    @Transactional
    @Override
    public CompletePaymentCallbackResult completePayment(
            Long userId, Long threadId, Long processId,
            CompletePaymentCallbackCommand command) {
        // 해당 값이 유효한지 체크
        Order order = paymentPort.getOrder(userId);
        Payment payment = order.getPayment();

        try {
            com.siot.IamportRestClient.response.Payment iamportResponse =
                    iamportClient.paymentByImpUid(command.uuid()).getResponse();
            String iamportPaymentStatus = iamportResponse.getStatus();

            // TODO 1. 포트원에서는 제대로 결제가 되었는지 확인하기
            if (!"paid".equals(iamportPaymentStatus)) {
                paymentPort.orderDelete(order);
                paymentPort.paymentDelete(order);
                throw new RuntimeException("결제 미완료");
            }

            // TODO 2. 포트원의 정보와 내가 저장한 값이 같은지 확인하기
            int actualAmount = iamportResponse.getAmount().intValue();
            Integer expectedAmount = order.getPrice();
            if (!expectedAmount.equals(actualAmount)) {
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getImpUid()
                        , true, new BigDecimal(actualAmount)));
                throw new RuntimeException("결제 금액 위변조");
            }

            // TODO 3. 확인 후, Payment 값 저장 이후 포트원에 결제 완료 API 보내기
            // 저장 시 값 바꿔주기
            Payment paymentToSave = paymentPort.getByPaymentId(payment.getPaymentId());
            paymentToSave.updatePayment();
            Payment savedPayment = paymentPort.savePaymentToComplete(paymentToSave);

            // TODO 4. 결제 이후 해당 결제에 관련된 데이터를 찾아서 모두 보내주기 (Order 도메인 조회)
            return CompletePaymentCallbackResult.from(savedPayment);
        } catch (IamportResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
