package com.peauty.domain.bidding;

public record PuppyId(Long value) {
    public boolean isSameId(Long id) {
        return value.equals(id);
    }
}