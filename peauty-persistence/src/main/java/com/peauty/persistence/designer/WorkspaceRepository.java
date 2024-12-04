package com.peauty.persistence.designer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface
WorkspaceRepository extends JpaRepository<WorkspaceEntity, Long> {
    WorkspaceEntity findByDesignerId(Long userId);

    boolean existsByDesignerId(Long designerId);
}
