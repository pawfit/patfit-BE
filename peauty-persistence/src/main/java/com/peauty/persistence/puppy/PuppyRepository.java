package com.peauty.persistence.puppy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PuppyRepository extends JpaRepository<PuppyEntity, Long> {
    Optional<PuppyEntity> findByIdAndCustomerId(Long puppyId, Long customerId);
}