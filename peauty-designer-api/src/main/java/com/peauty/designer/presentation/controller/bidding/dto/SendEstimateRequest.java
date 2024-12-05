package com.peauty.designer.presentation.controller.bidding.dto;

import com.peauty.designer.business.bidding.dto.SendEstimateCommand;

import java.time.LocalDate;


public record SendEstimateRequest(
        String content,
        Integer cost,
        LocalDate date,
        String proposalImageUrl
) {

    public SendEstimateCommand toCommand() {
        return SendEstimateCommand.builder()
                .content(content)
                .cost(cost)
                .date(date)
                .proposalImageUrl(proposalImageUrl)
                .build();
    }
}
