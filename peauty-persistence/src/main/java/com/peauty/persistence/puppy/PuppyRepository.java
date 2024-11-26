package com.peauty.persistence.puppy;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PuppyRepository extends JpaRepository<PuppyEntity, Long> {
    Optional<PuppyEntity> findByIdAndCustomerId(Long puppyId, Long customerId);
}