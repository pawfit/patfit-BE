package com.peauty.customer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.EstimateProposal;
import com.peauty.domain.bidding.EstimateProposalImage;
import com.peauty.domain.bidding.GroomingType;

import java.time.LocalDateTime;
import java.util.List;

public record SendEstimateProposalCommand(
        List<Long> designerIds,
        GroomingType groomingType,
        String detail,
        List<String> imageUrls,
        Long desiredCost,
        LocalDateTime desiredDateTime,
        String body,
        String face
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
                .body(body)
                .face(face)
                .build();
    }
}