package com.peauty.designer.implementation.bidding;

import com.peauty.designer.business.bidding.EstimatePort;
import com.peauty.domain.bidding.Estimate;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.persistence.bidding.estimate.*;
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
    public Estimate save(Estimate estimate) {
        EstimateEntity savedEstimateEntity = estimateRepository.save(
                EstimateMapper.toEstimateEntity(estimate)
        );
        List<EstimateImageEntity> imageEntities = estimate.getImages().stream()
                .map(image -> EstimateMapper.toImageEntity(image, savedEstimateEntity))
                .toList();
        List<EstimateImageEntity> savedImageEntities = estimateImageRepository.saveAll(imageEntities);
        return EstimateMapper.toEstimateDomain(savedEstimateEntity, savedImageEntities);
    }

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
    }}
