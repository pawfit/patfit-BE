package com.peauty.designer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.bidding.Estimate;
import com.peauty.domain.bidding.EstimateImage;
import com.peauty.domain.bidding.EstimateProposalImage;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record SendEstimateCommand(
        String content,
        String availableGroomingDate,
        String estimatedDuration,
        Long estimatedCost,
        List<String> imageUrls
) {

    public Estimate toEstimate(Long threadId) {
        return Estimate.builder()
                .id(null)
                .threadId(new BiddingThread.ID(threadId))
                .content(content)
                .availableGroomingDate(availableGroomingDate)
                .estimatedDuration(estimatedDuration)
                .estimatedCost(estimatedCost)
                .images(imageUrls.stream()
                        .map(imageUrl -> EstimateImage.builder()
                                .id(null)
                                .estimateId(null)
                                .imageUrl(imageUrl)
                                .build())
                        .toList())
                .build();
    }
}
