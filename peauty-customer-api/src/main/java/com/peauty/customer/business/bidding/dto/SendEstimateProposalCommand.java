package com.peauty.customer.business.bidding.dto;

import com.peauty.domain.bidding.*;

import java.time.LocalDateTime;
import java.util.List;

public record SendEstimateProposalCommand(
        List<Long> designerIds,
        GroomingType groomingType,
        String detail,
        List<String> imageUrls,
        Long desiredCost,
        String desiredDateTime,
        TotalGroomingBodyType totalGroomingBodyType,
        TotalGroomingFaceType totalGroomingFaceType
) {

    public EstimateProposal toEstimateProposal(BiddingProcess.ID processId) {
        return EstimateProposal.builder()
                .id(null)
                .processId(processId)
                .type(groomingType)
                .detail(detail)
                .images(imageUrls.stream()
                        .map(imageUrl -> EstimateProposalImage.builder()
                                .id(null)
                                .estimateProposalId(null)
                                .imageUrl(imageUrl)
                                .build())
                        .toList())
                .desiredCost(desiredCost)
                .desiredDateTime(desiredDateTime)
                .totalGroomingBodyType(totalGroomingBodyType)
                .totalGroomingFaceType(totalGroomingFaceType)
                .build();
    }
}