package com.peauty.domain.grooming;

public record CustomerId(Long value) {
    public boolean isSameId(Long id) {
        return value.equals(id);
    }
}