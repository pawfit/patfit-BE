package com.peauty.domain.bidding;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder
public class EstimateImage {

    private ID id;
    @Getter private Estimate.ID estimateId;
    @Getter private String imageUrl;

    public Optional<ID> getId() {
        return Optional.ofNullable(id);
    }

    public record ID(Long value) {
        public boolean isSameId(Long id) {
            return value.equals(id);
        }
    }
}