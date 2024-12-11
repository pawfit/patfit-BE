package com.peauty.payment.business;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.payment.Order;
import com.peauty.domain.payment.Payment;
import com.peauty.domain.response.PeautyResponseCode;
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

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentPort paymentPort;
    private final IamportClient iamportClient;

    // TODO. 1. 사전 검증 -> 추후에 제대로 수정
    @Transactional
    @Override
    public OrderResult saveOrder(Long userId, Long processId, Long threadId, OrderCommand command) {
        // TODO: 유저 검증
        // TODO: 요청서 & 견적서 정보 검증 (요청서, 견적서가 DB에 있는지 검증)
        Payment paymentToSave = Payment.initializePayment(command.depositPrice());
        Order orderToSave = command.toDomain(
                userId, processId, threadId,
                paymentToSave);
        orderToSave.updateOrderUuid();
        orderToSave.updateOrderStatus();
        orderToSave.transferReservationCost(orderToSave.getDepositPrice());
        orderToSave.getPayment().updateOrderId(orderToSave.getOrderId());



        Order savedOrder = paymentPort.saveOrder(orderToSave);
        return OrderResult.from(savedOrder);
    }

    // TODO: 2. 사후 검증 - 포트원에서 결제 정보 가져오기
    @Transactional
    @Override
    public CompletePaymentCallbackResult completePayment(
            Long userId, Long threadId, Long processId,
            CompletePaymentCallbackCommand command) {

        // OrderId에 해당하는 것을 DB에서 가져옴
        Payment payment = paymentPort.getByOrderId(command.orderId());

        try {
            // 프론트의 UUID가 실제로 포트원에 존재하는지 확인하기
            com.siot.IamportRestClient.response.Payment iamportResponse =
                    iamportClient.paymentByImpUid(command.paymentUuid()).getResponse();

            String iamportPaymentStatus = iamportResponse.getStatus();
            Long actualAmount = iamportResponse.getAmount().longValue();

            // 1. 포트원에서는 제대로 결제가 되었는지 확인하기
            if (payment.checkIfPaymentFailed(iamportPaymentStatus)) {
                payment.updateStatusToCancel();
                paymentPort.savePayment(payment);
            }

            log.info("payment depositPrice: {}\n actualAmount: {}", payment.getDepositPrice(), actualAmount);
            // 2. 포트원의 정보와 내가 저장한 값이 같은지 확인하기
            if (!payment.checkIfPaymentPriceEquals(actualAmount)) {
                payment.updateStatusToCancel();
                paymentPort.savePayment(payment);

                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getImpUid()
                        , true, new BigDecimal(actualAmount)));
                throw new PeautyException(PeautyResponseCode.PAYMENT_AMOUNT_NOT_EQUALS);
            }

            // 3. 검증 완료 후, Payment 값 저장
            payment.updateUuid(command.paymentUuid());
            payment.updateStatusToComplete();
            Payment savedPayment = paymentPort.savePayment(payment);

            // payment -> orderId -> threadId -> designerId -> workspaceName
            // Workspace 이름 구하기
            String workspaceName = paymentPort.findWorkspaceNameByThreadId(threadId);
            Long actualPrice = paymentPort.findActualPriceByThreadId(threadId);

            // ActualPrice 구하기
            return CompletePaymentCallbackResult.from(savedPayment, workspaceName, threadId, actualPrice);
        } catch (IamportResponseException e) {
            throw new PeautyException(PeautyResponseCode.IAMPORT_ERROR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
