package com.peauty.auth.client;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class AppleAuthClient implements ExternalAuthClient {

    @Value("${oauth.apple-public-key-url}")
    private String publicKeyUrl;
    private final RestClient restClient;

    @Override
    public OidcPublicKeyList getPublicKeys() {
        OidcPublicKeyList publicKeys = restClient.get()
                .uri(publicKeyUrl)
                .retrieve()
                .body(OidcPublicKeyList.class);

        if (publicKeys == null) {
            throw new PeautyException(PeautyResponseCode.APPLE_AUTH_CLIENT_ERROR);
        }

        return publicKeys;
    }


}