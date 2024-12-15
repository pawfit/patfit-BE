package com.peauty.persistence.designer.workspace;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerImageRepository extends JpaRepository<BannerImageEntity, Long> {
    List<BannerImageEntity> findByWorkspaceId(Long id);
}
