package com.peauty.domain.grooming;

public record PuppyId(Long value) {
    public boolean isSameId(Long id) {
        return value.equals(id);
    }
}