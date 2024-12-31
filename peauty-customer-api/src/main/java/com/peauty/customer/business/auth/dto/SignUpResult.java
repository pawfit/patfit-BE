package com.peauty.customer.business.auth.dto;

import com.peauty.domain.customer.Customer;
import com.peauty.domain.token.SignTokens;
import com.peauty.domain.user.AuthInfo;

public record SignUpResult(
        Long customerId,
        String accessToken,
        String refreshToken
) {
    public static SignUpResult from(SignTokens signTokens, Customer customer) {
        return new SignUpResult(
                customer.getCustomerId(),
                signTokens.accessToken(),
                signTokens.refreshToken()
        );
    }
}
