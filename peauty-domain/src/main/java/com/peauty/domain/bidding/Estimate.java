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
    @Getter private Long estimatedCost;
    @Getter private List<EstimateImage> images;

    public Optional<Estimate.ID> getId() {
        return Optional.ofNullable(this.id);
    }

    public ID getSavedEstimateId() {
        return id;
    }

    public Profile getProfile() {
        return Profile.builder()
                .id(id.value())
                .content(content)
                .availableGroomingDate(availableGroomingDate)
                .estimatedDuration(estimatedDuration)
                .estimatedCost(estimatedCost)
                .imageUrls(images.stream()
                        .map(EstimateImage::getImageUrl)
                        .toList()
                )
                .build();
    }

    @Builder
    public record Profile(
            Long id,
            String content,
            String availableGroomingDate,
            String estimatedDuration,
            Long estimatedCost,
            List<String> imageUrls
    ) {
    }
    
    public record ID(Long value) {
        public boolean isSameId(Long id) {
            return value.equals(id);
        }
    }
}
