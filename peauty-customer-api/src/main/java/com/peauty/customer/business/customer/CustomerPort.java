package com.peauty.customer.business.customer;

import com.peauty.customer.business.auth.dto.SignUpCommand;
import com.peauty.domain.user.User;

import java.util.Optional;

public interface CustomerPort {
    void checkCustomerSocialIdDuplicated(String socialId);
    void checkCustomerNicknameDuplicated(String nickname);
    void checkCustomerPhoneNumDuplicated(String phoneNum);
    Optional<User> findBySocialId(String socialId);
    User save(User user);
    User registerNewCustomer(SignUpCommand command);
}
