package com.peauty.customer.implementaion.bidding;

import com.peauty.customer.business.bidding.EstimatePort;
import com.peauty.domain.bidding.Estimate;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.persistence.bidding.estimate.EstimateEntity;
import com.peauty.persistence.bidding.estimate.EstimateImageEntity;
import com.peauty.persistence.bidding.estimate.EstimateImageRepository;
import com.peauty.persistence.bidding.estimate.EstimateRepository;
import com.peauty.persistence.bidding.mapper.EstimateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EstimateAdapter implements EstimatePort {

    private final EstimateRepository estimateRepository;
    private final EstimateImageRepository estimateImageRepository;

    @Override
    public Estimate getEstimateByEstimateId(Long estimateId) {
        EstimateEntity foundEstimateEntity = estimateRepository.findById(estimateId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_ESTIMATE));
        List<EstimateImageEntity> foundImageEntities = estimateImageRepository.findByEstimateId(foundEstimateEntity.getId());
        return EstimateMapper.toEstimateDomain(foundEstimateEntity, foundImageEntities);
    }

    @Override
    public Estimate getEstimateByThreadId(Long threadId) {
        EstimateEntity foundEstimateEntity = estimateRepository.findByBiddingThreadId(threadId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_ESTIMATE));
        List<EstimateImageEntity> foundImageEntities = estimateImageRepository.findByEstimateId(foundEstimateEntity.getId());
        return EstimateMapper.toEstimateDomain(foundEstimateEntity, foundImageEntities);
    }
}
