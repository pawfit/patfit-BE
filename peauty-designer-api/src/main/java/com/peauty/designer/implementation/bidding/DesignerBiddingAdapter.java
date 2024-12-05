package com.peauty.designer.implementation.bidding;

import com.peauty.designer.business.bidding.DesignerBiddingPort;
import com.peauty.domain.bidding.Estimate;
import com.peauty.persistence.bidding.estimate.DesignerBiddingRepository;
import com.peauty.persistence.bidding.estimate.EstimateEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class DesignerBiddingAdapter implements DesignerBiddingPort {

    private final DesignerBiddingRepository designerBiddingRepository;

    @Override
    public Estimate savedEstimate(
            Long userId,
            Long processId,
            Long threadId,
            Estimate estimate) {

        EstimateEntity estimateEntityToSave = BiddingMapper.toEstimateEntity(estimate);
        EstimateEntity savedEstimateEntity = designerBiddingRepository.save(estimateEntityToSave);
        return BiddingMapper.toEstimateDomain(savedEstimateEntity);
    }
}
