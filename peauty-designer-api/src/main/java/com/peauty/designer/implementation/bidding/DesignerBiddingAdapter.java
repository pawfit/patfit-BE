package com.peauty.designer.implementation.bidding;

import com.peauty.designer.business.bidding.DesignerBiddingPort;
import com.peauty.designer.business.bidding.Estimate;
import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.persistence.bidding.estimate.DesignerBiddingRepository;
import com.peauty.persistence.bidding.estimate.EstimateEntity;
import com.peauty.persistence.bidding.process.BiddingProcessEntity;
import com.peauty.persistence.bidding.process.BiddingProcessRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@AllArgsConstructor
public class DesignerBiddingAdapter implements DesignerBiddingPort {

    private final DesignerBiddingRepository designerBiddingRepository;
    private final BiddingProcessRepository biddingProcessRepository;

    @Override
    public Estimate sendEstimate(
            Long userId,
            Long processId,
            Long threadId,
            Estimate estimate) {

        // TODO DomainToEntity
        EstimateEntity estimateEntityToSave = DesignerBiddingMapper.toEntity(estimate);

        // TODO EntityToDomain
        EstimateEntity savedEstimateEntity = designerBiddingRepository.save(estimateEntityToSave);
        return DesignerBiddingMapper.toDomain(savedEstimateEntity);
    }

    @Override
    public Process isValidProcess(Long processId) {
        BiddingProcessEntity validProcessEntity = biddingProcessRepository.findById(processId);
        return null;
    }
}
