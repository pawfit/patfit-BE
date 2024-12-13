package com.peauty.payment.business;

import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.payment.Order;
import com.peauty.domain.payment.Payment;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.payment.business.dto.CompletePaymentCallbackCommand;
import com.peauty.payment.business.dto.CompletePaymentCallbackResult;
import com.peauty.payment.business.dto.CreateOrderCommand;
import com.peauty.payment.business.dto.CreateOrderResult;
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
    private final BiddingProcessPort biddingProcessPort;
    private final PuppyPort puppyPort;

    // TODO. 1. 사전 검증 -> 추후에 제대로 수정
    @Transactional
    @Override
    public CreateOrderResult saveOrder(
            Long userId, Long puppyId, Long processId, Long threadId,
            CreateOrderCommand command) {

        // TODO: 유저 검증
        // TODO: 요청서 & 견적서 정보 검증 (요청서, 견적서가 DB에 있는지 검증)
        Payment paymentToSave = Payment.initializePayment(command.depositPrice());

        Order orderToSave = command.toDomain(
                userId, processId, threadId,
                paymentToSave);
        orderToSave.registerOrderUuid();
        orderToSave.updateOrderToReady();
        orderToSave.getPayment().updateOrderId(orderToSave.getOrderId());

        // TODO. 실제 값도 저장해야 하나?
        Long actualPrice = orderToSave.getActualPrice();
        Long estimatePrice = paymentPort.findActualPriceByThreadId(threadId);

        // TODO. validatePrice 메서드를 Order에 넣을까, Payment에 넣을까
        // 현재는 NULL만 체크하는데 추후에 견적서에 적힌 값과 앞으로 결제할 금액이 동일한지 체크
        orderToSave.getPayment().validatePrice(actualPrice, estimatePrice);

        Order savedOrder = paymentPort.saveOrder(orderToSave);
        return CreateOrderResult.from(savedOrder);
    }

    // TODO: 2. 사후 검증 - 포트원에서 결제 정보 가져오기
    @Transactional
    @Override
    public CompletePaymentCallbackResult acceptEstimate(
            Long userId, Long puppyId, Long processId,Long threadId,
            CompletePaymentCallbackCommand command) {

        Order order = paymentPort.findOrderById(command.orderId());
        Payment paymentToValidate = order.getPayment();
        BiddingProcess processToChangeStatus = biddingProcessPort.getProcessByProcessId(processId);

        try {
            com.siot.IamportRestClient.response.Payment iamportResponse =
                    iamportClient.paymentByImpUid(command.paymentUuid()).getResponse();
            String iamportPaymentStatus = iamportResponse.getStatus();
            Long actualAmount = iamportResponse.getAmount().longValue();

            // 1. 포트원에서는 제대로 결제가 되었는지 확인하기
            if (paymentToValidate.checkIfPaymentFailed(iamportPaymentStatus)) {
                order.updateOrderToCanceled();
                paymentToValidate.updateStatusToCancel();
                processToChangeStatus.cancelThread(new BiddingThread.ID(threadId));
                paymentPort.savePayment(paymentToValidate);
            }

            // 2. 포트원의 정보와 내가 저장한 값이 같은지 확인하기
            // TODO. checkIfPaymentPriceEquals 조금 더 직관적인 메서드명으로 리팩토링하기
            if (!paymentToValidate.checkIfPaymentPriceEquals(actualAmount)) {
                order.updateOrderToCanceled();
                paymentToValidate.updateStatusToCancel();
                paymentPort.savePayment(paymentToValidate);
                processToChangeStatus.cancelThread(new BiddingThread.ID(threadId));
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getImpUid()
                        , true, new BigDecimal(actualAmount)));
                throw new PeautyException(PeautyResponseCode.PAYMENT_AMOUNT_NOT_EQUALS);
            }

            // 3. 검증 완료 후, Payment, Order 상태 변화, 화면에 필요한 데이터 조회
            paymentToValidate.updateUuid(command.paymentUuid());
            paymentToValidate.updateStatusToComplete();
            order.updateOrderToCompleted();

            // 4. 프로세스 - 쓰레드 상태 변화
            puppyPort.verifyPuppyOwnership(puppyId, userId);
            BiddingProcess process = biddingProcessPort.getProcessByProcessIdAndPuppyId(processId, puppyId);
            process.reserveThread(new BiddingThread.ID(threadId));
            BiddingProcess savedProcess = biddingProcessPort.save(process);
            BiddingThread reservedThread = savedProcess.getThread(new BiddingThread.ID(threadId));

            // 5. Payment - Order DB 저장
            order.updateValidatedPayment(paymentToValidate);
            paymentPort.saveOrder(order);
            Payment savedPayment = paymentPort.savePayment(paymentToValidate);
            String workspaceName = paymentPort.findWorkspaceNameByThreadId(reservedThread.getSavedThreadId().value());
            Long actualPrice = paymentPort.findActualPriceByThreadId(reservedThread.getSavedThreadId().value());

            return CompletePaymentCallbackResult.from(savedPayment, workspaceName, threadId, actualPrice);
        } catch (IamportResponseException | IOException e) {
            throw new PeautyException(PeautyResponseCode.IAMPORT_ERROR);
        }
    }
}
