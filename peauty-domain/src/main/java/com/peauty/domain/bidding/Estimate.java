package com.peauty.domain.bidding;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Estimate {

    private final ID id;
    @Getter private final BiddingThread.ID threadId;
    @Getter private String content;
    @Getter private String availableGroomingDate; // TODO 몇년 몇월 몇일로 관리할 클래스 만들기
    @Getter private String estimatedDuration; // TODO 시, 분으로 관리할 클래스 만들기
    @Getter private Long estimatedCost; // TODO cost -> price
    @Getter private List<EstimateImage> images;

    public Optional<Estimate.ID> getId() {
        return Optional.ofNullable(this.id);
    }

    public ID getSavedEstimateId() {
        return id;
    }

    // TODO 예약금 퍼센트를 관리할 estimatedCost 의 값 객체가 필요합니다
    public Long getDepositPrice() {
        return (long) (estimatedCost * (0.5));
    }

    public Profile getProfile() {
        return Profile.builder()
                .estimateId(id.value())
                .threadId(threadId.value())
                .content(content)
                .availableGroomingDate(availableGroomingDate)
                .estimatedDuration(estimatedDuration)
                .estimatedCost(estimatedCost)
                .depositPrice(getDepositPrice())
                .imageUrls(images.stream()
                        .map(EstimateImage::getImageUrl)
                        .toList()
                )
                .build();
    }

    @Builder
    public record Profile(
            Long threadId,
            Long estimateId,
            String content,
            String availableGroomingDate,
            String estimatedDuration,
            Long estimatedCost,
            Long depositPrice,
            List<String> imageUrls
    ) {
    }

    public record ID(Long value) {
        public boolean isSameId(Long id) {
            return value.equals(id);
        }
    }
}
