package com.peauty.designer.presentation.controller.bidding.dto;

import com.peauty.designer.business.bidding.dto.SendEstimateCommand;

import java.time.LocalDate;
import java.util.List;


public record SendEstimateRequest(
        String content,
        String availableGroomingDate,
        String estimatedDuration,
        Long estimatedCost,
        List<String> imageUrls
) {

    public SendEstimateCommand toCommand() {
        return SendEstimateCommand.builder()
                .content(content)
                .availableGroomingDate(availableGroomingDate)
                .estimatedDuration(estimatedDuration)
                .estimatedCost(estimatedCost)
                .imageUrls(imageUrls)
                .build();
    }
}
