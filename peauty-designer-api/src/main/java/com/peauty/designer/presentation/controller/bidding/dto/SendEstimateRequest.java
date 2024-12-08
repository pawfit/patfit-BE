package com.peauty.designer.presentation.controller.bidding.dto;

import com.peauty.designer.business.bidding.dto.SendEstimateCommand;

import java.time.LocalDate;
import java.util.List;


public record SendEstimateRequest(
        String content,
//        String availableGroomingDate,
        String estimatedDuration,
        Long estimatedCost,
        List<String> imageUrls
) {

    public SendEstimateCommand toCommand() {
        return SendEstimateCommand.builder()
                .content(content)
                .availableGroomingDate("matches customer needs")  // TODO 채팅 개발 후 고객과 디자이너가 논의가 가능해지면 인풋으로 받기
                .estimatedDuration(estimatedDuration)
                .estimatedCost(estimatedCost)
                .imageUrls(imageUrls)
                .build();
    }
}
