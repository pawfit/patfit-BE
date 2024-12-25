package com.peauty.domain.bidding;

public record DesignerId(Long value) {
    public boolean isSameId(Long id) {
        return value.equals(id);
    }
}