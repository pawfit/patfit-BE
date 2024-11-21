package com.peauty.auth.client;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.auth.properties.OAuthProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoAuthClient implements ExternalAuthClient {

    private final RestClient restClient;
    private final OAuthProperties oAuthProperties;

    @Override
    public OidcPublicKeyList getPublicKeys() {
        OidcPublicKeyList publicKeys = restClient.get()
                .uri(oAuthProperties.kakaoPublicKeyInfo())
                .retrieve()
                .body(OidcPublicKeyList.class);

        if (publicKeys == null) {
            throw new PeautyException(PeautyResponseCode.KAKAO_AUTH_CLIENT_ERROR);
        }

        return publicKeys;
    }

    public String getIdTokenFromKakao(String authCode) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", oAuthProperties.kakaoClientId());
        formData.add("redirect_uri", oAuthProperties.kakaoRedirectUrl());
        formData.add("code", authCode);
        String response = restClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .body(formData)
                .retrieve()
                .body(String.class);
        try {
            JsonNode node = new ObjectMapper().readTree(response);
            return node.get("id_token").asText();
        } catch (Exception e) {
            throw new PeautyException(PeautyResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}