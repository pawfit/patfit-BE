package com.peauty.designer.business.designer;

import com.peauty.designer.business.auth.dto.SignUpCommand;
import com.peauty.domain.designer.Designer;

import java.util.Optional;

public interface DesignerPort {
    void checkCustomerNicknameDuplicated(String nickname);

    void checkCustomerPhoneNumDuplicated(String phoneNum);

    Optional<Designer> findBySocialId(String socialId);

    Designer save(Designer designer);

    Designer registerNewDesigner(SignUpCommand command);

    Designer getByDesignerId(Long designerId);

    Designer getAllDesignerDataByDesignerId(Long userId);
}

