package com.peauty.customer.presentation.controller.bidding.dto;

import com.peauty.customer.business.bidding.dto.SendEstimateProposalCommand;
import com.peauty.domain.bidding.GroomingType;
import com.peauty.domain.bidding.TotalGroomingBodyType;
import com.peauty.domain.bidding.TotalGroomingFaceType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record SendEstimateProposalRequest(
        List<Long> designerIds,
        GroomingType groomingType,
        String detail,
        List<String> imageUrls,
        Long desiredCost,
        @Schema(description = "미용을 원하는 시간입니다 지금은 예시처럼 타입을 맞춰주세요!", example = "2024-12-18 15:45")
        String desiredDateTime,
        TotalGroomingBodyType totalGroomingBodyType,
        TotalGroomingFaceType totalGroomingFaceType
) {
    public SendEstimateProposalCommand toCommand() {
        return new SendEstimateProposalCommand(
                designerIds,
                groomingType,
                detail,
                imageUrls,
                desiredCost,
                desiredDateTime,
                totalGroomingBodyType,
                totalGroomingFaceType
        );
    }
}
