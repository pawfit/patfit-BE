package com.peauty.domain.grooming;

public record DesignerId(Long value) {
    public boolean isSameId(Long id) {
        return value.equals(id);
    }
}