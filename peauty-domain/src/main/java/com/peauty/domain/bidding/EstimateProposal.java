package com.peauty.domain.bidding;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Builder
public class EstimateProposal {

    private ID id;
    @Getter private BiddingProcess.ID processId;
    @Getter private GroomingType type;
    @Getter private String detail;
    @Getter private List<EstimateProposalImage> images;
    @Getter private Long desiredCost;
    @Getter private String desiredDateTime;

    // can be null depends on grooming type
    @Getter private TotalGroomingBodyType totalGroomingBodyType;
    @Getter private TotalGroomingFaceType totalGroomingFaceType;

    public Optional<EstimateProposal.ID> getId() {
        return Optional.ofNullable(id);
    }

    public String getSimpleGroomingStyle() {
        if (type.isCleanGrooming()) {
            return type.getDescription();
        }
        String faceType = totalGroomingFaceType.getDescription();
        String bodyType = totalGroomingBodyType.getBaseStyleName();
        return faceType +  " + " + bodyType;
    }

    public Profile getProfile() {
        return Profile.builder()
                .id(id.value())
                .style(getSimpleGroomingStyle())
                .totalGroomingBodyType(totalGroomingBodyType == null ? null : totalGroomingBodyType.getDescription())
                .totalGroomingFaceType(totalGroomingFaceType == null ? null : totalGroomingFaceType.getDescription())
                .detail(detail)
                .imageUrls(images.stream()
                        .map(EstimateProposalImage::getImageUrl)
                        .toList()
                )
                .desiredCost(desiredCost)
                .desiredDateTime(desiredDateTime)
                .build();
    }

    @Builder
    public record Profile(
            Long id,
            String style,
            String totalGroomingBodyType,
            String totalGroomingFaceType,
            String detail,
            List<String> imageUrls,
            Long desiredCost,
            String desiredDateTime
    ) {
    }

    public record ID(Long value) {
        public boolean isSameId(Long id) {
            return value.equals(id);
        }
    }
}