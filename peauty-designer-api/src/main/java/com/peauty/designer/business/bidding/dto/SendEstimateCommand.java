package com.peauty.designer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.bidding.Estimate;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SendEstimateCommand(
        String content,
        Integer cost,
        LocalDate date,
        String proposalImageUrl
) {
    public Estimate toEstimate(BiddingThread.ID threadId) {
        return Estimate.builder()
                .id(null)
                .threadId(threadId)
                .content(content)
                .date(date)
                // TODO: 리스트로 만들기, 리스트 엔티티 생성
                .proposalImageUrl(proposalImageUrl)
                .cost(cost)
                .build();
    }
}
