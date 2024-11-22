package com.peauty.persistence.designer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DesignerRepository extends JpaRepository<DesignerEntity, Long> {
    boolean existsByNickname(String nickname);
    boolean existsByPhoneNum(String phoneNum);
    Optional<DesignerEntity> findBySocialId(String socialId);
}
