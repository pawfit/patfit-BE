package com.peauty.designer.business.designer;

import com.peauty.designer.business.auth.dto.SignUpCommand;
import com.peauty.domain.user.User;

import java.util.Optional;

public interface DesignerPort {
    void checkCustomerNicknameDuplicated(String nickname);
    void checkCustomerPhoneNumDuplicated(String phoneNum);
    Optional<User> findBySocialId(String socialId);
    User save(User user);
    User registerNewDesigner(SignUpCommand command);
}

