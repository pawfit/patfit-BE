package com.peauty.payment.business;

import com.peauty.domain.bidding.*;
import com.peauty.domain.payment.*;
import com.peauty.payment.business.dto.*;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.exception.PeautyException;
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
                userId, processId, threadId, OrderStatus.READY,
                paymentToSave);
        orderToSave.registerOrderUuid();
        orderToSave.updateOrderToReady();
        orderToSave.getPayment().updateOrderId(orderToSave.getOrderId());

        // TODO. 실제 값도 저장해야 하나?
        Long actualPrice = orderToSave.getActualPrice();
        Long estimatePrice = paymentPort.findActualPriceByThreadId(threadId);

        // TODO. validatePrice 메서드를 Order에 넣을까, Payment에 넣을까
        // TODO. 현재는 NULL만 체크하는데 추후에 견적서에 적힌 값과 앞으로 결제할 금액이 동일한지 체크
        orderToSave.getPayment().validatePrice(actualPrice, estimatePrice);
        Order savedOrder = paymentPort.saveOrder(orderToSave);
        return CreateOrderResult.from(savedOrder);
    }

    // TODO: 2. 사후 검증 - 포트원에서 결제 정보 가져오기
    @Transactional
    @Override
    public CompletePaymentCallbackResult acceptEstimate(
            Long userId, Long puppyId, Long processId, Long threadId,
            CompletePaymentCallbackCommand command) {

        Order orderToValidate = paymentPort.findOrderById(command.orderId());
        Payment paymentToValidate = orderToValidate.getPayment();

        try {
            com.siot.IamportRestClient.response.Payment iamportResponse =
                    iamportClient.paymentByImpUid(command.paymentUuid()).getResponse();
            String iamportPaymentStatus = iamportResponse.getStatus();
            Long actualAmount = iamportResponse.getAmount().longValue();

            if (paymentToValidate.checkPortonePaymentStatusNotPaied(iamportPaymentStatus)) {
                orderToValidate.updateStatusToError();
                paymentToValidate.updateStatusToError();
            }

            if (paymentToValidate.checkPaymentPriceEqualsWithPortonePrice(actualAmount)) {
                orderToValidate.updateStatusToError();
                paymentToValidate.updateStatusToError();
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getImpUid()
                        , true, new BigDecimal(actualAmount)));
            }

            paymentToValidate.updateUuid(command.paymentUuid());
            puppyPort.verifyPuppyOwnership(puppyId, userId);
            BiddingProcess process = biddingProcessPort.getProcessByProcessIdAndPuppyId(processId, puppyId);
            BiddingThread thread;

            if (orderToValidate.getOrderStatus().equals(OrderStatus.ERROR)
                    && paymentToValidate.getStatus().equals(PaymentStatus.ERROR)) {
                thread = process.getThread(new BiddingThread.ID(threadId));
            } else {
                paymentToValidate.updateStatusToComplete();
                orderToValidate.updateOrderToCompleted();

                process.reserveThread(new BiddingThread.ID(threadId));
                BiddingProcess savedProcess = biddingProcessPort.save(process);
                thread = savedProcess.getThread(new BiddingThread.ID(threadId));
            }
            return processAndSavePayment(orderToValidate, paymentToValidate, thread);
        } catch (IamportResponseException | IOException e) {
            throw new PeautyException(PeautyResponseCode.IAMPORT_ERROR);
        }
    }

    private CompletePaymentCallbackResult processAndSavePayment(
            Order order, Payment paymentToValidate, BiddingThread thread) {
        order.updateValidatedPayment(paymentToValidate);
        paymentPort.saveOrder(order);

        Payment savedPayment = paymentPort.savePayment(order.getPayment());
        String workspaceName = paymentPort.findWorkspaceNameByThreadId(thread.getSavedThreadId().value());
        Long actualPrice = paymentPort.findActualPriceByThreadId(thread.getSavedThreadId().value());

        return CompletePaymentCallbackResult.from(
                savedPayment,
                workspaceName,
                thread.getSavedThreadId().value(),
                actualPrice);
    }
}
