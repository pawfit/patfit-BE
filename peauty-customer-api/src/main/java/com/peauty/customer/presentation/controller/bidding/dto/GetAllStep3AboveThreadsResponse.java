package com.peauty.customer.presentation.controller.bidding.dto;

import com.peauty.customer.business.bidding.dto.GetAllStep3AboveThreadsResult;
import com.peauty.domain.designer.Badge;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

public record GetAllStep3AboveThreadsResponse(
        @Schema(description = "스레드 목록")
        List<GetAllStep3AboveThreadsResponseThread> threads
) {
    @Builder
    public record GetAllStep3AboveThreadsResponseThread(

            @Schema(
                    description = "프로세스 ID",
                    example = "10"
            )
            Long processId,

            @Schema(
                    description = "스레드 ID",
                    example = "93"
            )
            Long threadId,

            @Schema(
                    description = "스레드 단계",
                    example = "견적응답"
            )
            String threadStep,

            @Schema(
                    description = "스레드 상태",
                    example = "정상"
            )
            String threadStatus,

            @Schema(
                    description = "썸네일 이미지 URL",
                    example = "https://example.com/images/thumbnail.jpg"
            )
            String thumbnailUrl,

            @Schema(
                    description = "작업공간 이름",
                    example = "댕댕살롱 역삼점"
            )
            String workspaceName,

            @Schema(
                    description = "평점",
                    example = "4.8"
            )
            Double score,

            @Schema(
                    description = "리뷰 수",
                    example = "127"
            )
            Integer reviewCount,

            @Schema(
                    description = "작업공간 주소",
                    example = "강남구 역삼동"
            )
            String address,

            @Schema(
                    description = "미용 스타일",
                    example = "위생미용"
            )
            String style,

            @Schema(
                    description = "견적 비용",
                    example = "50000"
            )
            Long estimatedCost,

            @Schema(
                    description = "뱃지 목록",
                    example = """
                            [
                                {
                                    "badgeId": 1,
                                    "badgeName": "친절한",
                                    "badgeContent": "친절한 서비스로 고객 만족도가 높은 디자이너입니다",
                                    "badgeImageUrl": "https://example.com/badges/kind.png",
                                    "isRepresentativeBadge": true,
                                    "badgeColor": "GOLD",
                                    "badgeType": "GENERAL"
                                },
                                {
                                    "badgeId": 2,
                                    "badgeName": "전문가",
                                    "badgeContent": "5년 이상의 전문 경력을 보유한 디자이너입니다",
                                    "badgeImageUrl": "https://example.com/badges/expert.png",
                                    "isRepresentativeBadge": false,
                                    "badgeColor": "SILVER",
                                    "badgeType": "SCISSORS"
                                }
                            ]
                            """
            )
            List<Badge> badges
    ) {
    }

    public static GetAllStep3AboveThreadsResponse from(GetAllStep3AboveThreadsResult result) {
        return new GetAllStep3AboveThreadsResponse(
                result.threads().stream()
                        .map(thread -> GetAllStep3AboveThreadsResponseThread.builder()
                                .processId(thread.processId())
                                .threadId(thread.threadId())
                                .threadStep(thread.threadStep())
                                .threadStatus(thread.threadStatus())
                                .thumbnailUrl(thread.designer().profileImageUrl())
                                .workspaceName(thread.designer().workspaceName())
                                .score(thread.designer().reviewRating())
                                .reviewCount(thread.designer().reviewCount())
                                .address(thread.designer().address())
                                .style(thread.style())
                                .estimatedCost(thread.estimate().estimatedCost())
                                .badges(thread.designer().badges())
                                .build())
                        .toList()
        );
    }
}