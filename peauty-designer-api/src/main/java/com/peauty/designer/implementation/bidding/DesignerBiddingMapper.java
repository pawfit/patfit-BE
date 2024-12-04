package com.peauty.designer.implementation.bidding;

import com.peauty.designer.business.bidding.Estimate;
import com.peauty.persistence.bidding.estimate.EstimateEntity;
import org.springframework.stereotype.Component;

@Component
public class DesignerBiddingMapper {

    public static EstimateEntity toEntity(Estimate estimate) {
        return null;
    }

    public static Estimate toDomain(EstimateEntity savedEstimateEntity) {
        return null;
    }
}
