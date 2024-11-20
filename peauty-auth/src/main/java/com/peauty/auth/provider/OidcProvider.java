package com.peauty.auth.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.Base64;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.user.SocialInfo;

import java.io.IOException;
import java.util.Map;

public interface OidcProvider {

    SocialInfo getSocialInfo(String idToken);
    String getIdToken(String authCode);

    @SuppressWarnings("unchecked")
    default Map<String, String> parseHeaders(String token) {
        String header = token.split("\\.")[0];
        try {
            return new ObjectMapper().readValue(
                    Base64.decodeBase64(header),
                    Map.class
            );
        } catch (IOException e) {
            throw new PeautyException(PeautyResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}