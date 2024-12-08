package com.peauty.persistence.designer.workspace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface
WorkspaceRepository extends JpaRepository<WorkspaceEntity, Long> {
    Optional<WorkspaceEntity> findByDesignerId(Long userId);

    boolean existsByDesignerId(Long designerId);

    Optional<WorkspaceEntity> findAllByDesignerId(Long designerId);
    List<WorkspaceEntity> findByAddress(String address);

    @Query("SELECT w FROM WorkspaceEntity w WHERE w.address LIKE CONCAT(:baseAddress, '%')")
    List<WorkspaceEntity> findByBaseAddress(@Param("baseAddress") String baseAddress);

}
