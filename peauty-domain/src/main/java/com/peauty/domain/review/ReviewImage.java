package com.peauty.domain.review;

import lombok.*;

import java.util.Optional;

@Builder
public class ReviewImage {

    private ID id;
    @Getter private Review.ID reviewId;
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
