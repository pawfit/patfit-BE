package com.peauty.customer.business.auth.dto;

import com.peauty.domain.user.OidcProviderType;

public record SignUpCommand(
        String oidcProviderId,
        OidcProviderType oidcProviderType,
        String name,
        String phoneNum,
        String region,
        String nickname,
        String profileImageUrl
) {

}
