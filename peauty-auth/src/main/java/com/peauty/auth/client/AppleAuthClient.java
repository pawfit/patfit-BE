package com.peauty.auth.client;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.auth.properties.OAuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class AppleAuthClient implements ExternalAuthClient {

    private final RestClient restClient;
    private final OAuthProperties oAuthProperties;

    @Override
    public OidcPublicKeyList getPublicKeys() {
        OidcPublicKeyList publicKeys = restClient.get()
                .uri(oAuthProperties.applePublicKeyUrl())
                .retrieve()
                .body(OidcPublicKeyList.class);

        if (publicKeys == null) {
            throw new PeautyException(PeautyResponseCode.APPLE_AUTH_CLIENT_ERROR);
        }

        return publicKeys;
    }


}