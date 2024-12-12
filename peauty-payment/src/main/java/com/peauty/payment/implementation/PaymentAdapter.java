package com.peauty.payment.implementation;

import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.bidding.Estimate;
import com.peauty.domain.designer.Workspace;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.payment.Order;
import com.peauty.domain.payment.Payment;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.payment.business.PaymentPort;
import com.peauty.persistence.bidding.estimate.EstimateEntity;
import com.peauty.persistence.bidding.estimate.EstimateRepository;
import com.peauty.persistence.bidding.mapper.BiddingMapper;
import com.peauty.persistence.bidding.mapper.EstimateMapper;
import com.peauty.persistence.bidding.thread.BiddingThreadEntity;
import com.peauty.persistence.bidding.thread.BiddingThreadRepository;
import com.peauty.persistence.designer.mapper.WorkspaceMapper;
import com.peauty.persistence.designer.workspace.WorkspaceEntity;
import com.peauty.persistence.designer.workspace.WorkspaceRepository;
import com.peauty.persistence.payment.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentAdapter implements PaymentPort {

    // TODO. orderPort 로
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final WorkspaceRepository workspaceRepository;
    private final BiddingThreadRepository biddingThreadRepository;
    private final EstimateRepository estimateRepository;

    public Order saveOrder(Order order) {
        OrderEntity orderEntityToSave = PaymentMapper.toOrderEntity(order);
        OrderEntity savedOrderEntity = orderRepository.save(orderEntityToSave);
        order.getPayment().updateOrderId(savedOrderEntity.getId());
        Payment savedPayment = savePayment(order.getPayment());
        return PaymentMapper.toOrderDomain(savedOrderEntity, savedPayment);
    }

    @Override
    public Payment getByOrderId(Long orderId) {
        PaymentEntity paymentEntity = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_PAYMENT));
        return PaymentMapper.toPaymentDomain(paymentEntity);
    }

    @Override
    public Order findOrderById(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_ORDER));
        return PaymentMapper.toOrderDomain(orderEntity, getByOrderId(orderId));
    }

    @Override
    public Payment savePayment(Payment payment) {
        PaymentEntity paymentEntityToSave = PaymentMapper.toPaymentEntity(payment);
        PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntityToSave);
        return PaymentMapper.toPaymentDomain(savedPaymentEntity);
    }

    @Override
    public String findWorkspaceNameByThreadId(Long threadId) {
        BiddingThreadEntity biddingThreadEntity = biddingThreadRepository.findById(threadId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_BIDDING_THREAD_IN_PROCESS));

        BiddingThread findThread = BiddingMapper.toThreadDomain(biddingThreadEntity);
        Long designerId = findThread.getDesignerId().value();

        WorkspaceEntity workspaceEntity = workspaceRepository.findByDesignerId(designerId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_WORKSPACE));

        Workspace findWorkspace = WorkspaceMapper.toDomain(workspaceEntity);
        return findWorkspace.getWorkspaceName();
    }

    @Override
    public Long findActualPriceByThreadId(Long threadId) {
        BiddingThreadEntity biddingThreadEntity = biddingThreadRepository.findById(threadId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_BIDDING_THREAD_IN_PROCESS));
        BiddingThread findThread = BiddingMapper.toThreadDomain(biddingThreadEntity);

        // TODO. Review 요청 VO를 이렇게 사용하는게 맞는지 모르겠다.
        EstimateEntity estimateEntity = estimateRepository.findByBiddingThreadId(findThread.getSavedThreadId().value())
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_ESTIMATE));

        Estimate estimate = EstimateMapper.toEstimateDomain(estimateEntity, List.of());
        return estimate.getEstimatedCost();
    }
}
