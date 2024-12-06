package com.peauty.designer.business.designer;

import com.peauty.designer.business.auth.dto.SignUpCommand;
import com.peauty.domain.designer.Badge;
import com.peauty.domain.designer.Designer;

import java.util.List;
import java.util.Optional;

public interface DesignerPort {
    void checkDesignerNicknameDuplicated(String nickname);

    void checkDesignerPhoneNumDuplicated(String phoneNum);

    Optional<Designer> findBySocialId(String socialId);

    Designer save(Designer designer);

    Designer registerNewDesigner(SignUpCommand command);

    Designer getByDesignerId(Long designerId);

    Designer getAllDesignerDataByDesignerId(Long userId);

    List<Badge> getBadges(Long userId);

    List<Badge> getAllBadges(); // 전체 뱃지

    List<Badge> getAcquiredBadges(Long userId);

}

