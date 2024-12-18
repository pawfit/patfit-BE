package com.peauty.customer.presentation.controller.bidding.dto;

import com.peauty.customer.business.bidding.dto.GetOngoingProcessWithThreadsResult;
import com.peauty.domain.designer.Badge;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.format.DateTimeFormatter;
import java.util.List;

public record GetOngoingProcessWithStep2ThreadsResponse(
        @Schema(description = "프로세스 정보")
        GetOngoingProcessWithStep2ThreadsResponseProcessInfo info,
        @Schema(description = "매장 리스트")
        List<GetOngoingProcessWithStep2ThreadsResponseItem> stores
) {

    @Builder
    public record GetOngoingProcessWithStep2ThreadsResponseProcessInfo(
            @Schema(
                    description = "요청 날짜",
                    example = "2024-12-18"
            )
            String requestDate,

            @Schema(
                    description = "요청 내용",
                    example = "위생 미용"
            )
            String requestText,

            @Schema(
                    description = "프로세스 ID",
                    example = "10"
            )
            Long processId
    ) {
    }

    @Builder
    public record GetOngoingProcessWithStep2ThreadsResponseItem(

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
                    description = "미용 스타일",
                    example = "위생미용"
            )
            String style,

            @Schema(
                    description = "위치",
                    example = "강남구 역삼동"
            )
            String location,

            @Schema(
                    description = "매장명",
                    example = "댕댕살롱 역삼점"
            )
            String store,

            @Schema(
                    description = "평점",
                    example = "4.8"
            )
            Double score,

            @Schema(
                    description = "리뷰 수",
                    example = "127"
            )
            Integer review,


            @Schema(
                    description = "디자이너 요청 가격"
            )
            Long desiredCost,

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

    public static GetOngoingProcessWithStep2ThreadsResponse from(GetOngoingProcessWithThreadsResult result) {
        if (result == null) {
            return new GetOngoingProcessWithStep2ThreadsResponse(null, null);
        }
        return new GetOngoingProcessWithStep2ThreadsResponse(
                GetOngoingProcessWithStep2ThreadsResponseProcessInfo.builder()
                        .requestDate(result.process().processCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .requestText(result.process().estimateProposal().style())
                        .processId(result.process().processId())
                        .build(),
                result.threads().stream()
                        .map(thread -> GetOngoingProcessWithStep2ThreadsResponseItem.builder()
                                .threadId(thread.threadId())
                                .threadStep(thread.threadStep())
                                .threadStatus(thread.threadStatus())
                                .style(thread.style())
                                .location(thread.designer().address())
                                .store(thread.designer().workspaceName())
                                .score(thread.designer().reviewRating())
                                .review(thread.designer().reviewCount())
                                .badges(thread.designer().badges())
                                .thumbnailUrl(thread.designer().profileImageUrl())
                                .desiredCost(thread.estimate().estimatedCost())
                                .build())
                        .toList()
        );
    }
}
