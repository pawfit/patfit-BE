package com.peauty.designer.presentation.controller.designer.dto;

import com.peauty.designer.business.designer.dto.GetDesignerWorkspaceResult;
import com.peauty.domain.designer.PaymentOption;
import com.peauty.domain.designer.Scissor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record GetDesignerWorkspaceResponse(
        @Schema(description = "디자이너 ID (PK)", example = "1")
        Long designerId,
        @Schema(description = "워크 스페이스 ID (PK)", example = "2")
        Long workspaceId,
        @Schema(description = "배너 이미지 URL", example = "이미지 url")
        String bannerImageUrl,
        @Schema(description = "가게 이름", example = "호키포키")
        String workspaceName,
        @Schema(description = "평점", example = "4.5")
        Double reviewRating,
        @Schema(description = "리뷰 수", example = "5")
        Integer reviewsCount,
        @Schema(description = "가위 종류", example = "GOLD")
        Scissor scissors,
        @Schema(description = "소개 제목", example = "호키포키에 오신 여러분들 환영합니다!")
        String introduceTitle,
        @Schema(description = "소개", example = "안녕하세요. 말티즈 및 푸들 모발 케어 호키포키입니다.")
        String introduce,
        @Schema(description = "공지사항", example = "5주년 특별 공지 사항")
        String noticeTitle,
        @Schema(description = "공지사항", example = "저희 호키포키에서 5주년 이벤트로 예약 자에 한에서 반려견 피부 보습 케어..")
        String notice,
        @Schema(description = "주소", example = "성남시 위례구 사미동")
        String address,
        @Schema(description = "상세 주소", example = "대성 베르힐 1401호")
        String addressDetail,
        @Schema(description = "전화번호", example = "01011112222")
        String phoneNumber,
        @Schema(description = "경력 연수", example = "5")
        Integer yearOfExperience,
        @Schema(description = "오픈 시간", example = "10:00")
        String openHours,
        @Schema(description = "마감 시간", example = "20:00")
        String closeHours,
        @Schema(description = "영업일", example = "주말휴무")
        String openDay,
        @Schema(description = "매장까지의 거리 정보(시간)", example = "위례역 도보 3분 거리")
        String directionGuide,
        @Schema(description = "자격증 이미지 URL들", example = "[\"자격증 이미지 url1\", \"자격증 이미지 url2\"]")
        List<String> licenses,
        @Schema(description = "결제 방식", example = "[\"ACCOUNT\", \"CASH\", \"CARD\"]")
        List<PaymentOption> paymentOptions,
        @Schema(description = "대표 배지 이름들", example = "[\"사업자 등록 인증\", \"말티즈 전문가\"]")
        List<String> representativeBadgeNames


) {
    public static GetDesignerWorkspaceResponse from(GetDesignerWorkspaceResult result) {
        return new GetDesignerWorkspaceResponse(
                result.designerId(),
                result.workspaceId(),
                result.bannerImageUrl(),
                result.workspaceName(),
                result.reviewRating(),
                result.reviewsCount(),
                result.scissor(),
                result.introduceTitle(),
                result.introduce(),
                result.noticeTitle(),
                result.notice(),
                result.address(),
                result.addressDetail(),
                result.phoneNumber(),
                result.yearOfExperience(),
                result.openHours(),
                result.closeHours(),
                result.openDays(),
                result.directionGuide(),
                result.licenses(),
                result.paymentOptions(),
                result.representativeBadgeNames()
                );
    }
}
