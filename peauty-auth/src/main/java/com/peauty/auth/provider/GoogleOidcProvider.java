package com.peauty.auth.provider;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.user.SocialPlatform;
import com.peauty.domain.user.SocialInfo;
import com.peauty.auth.properties.OAuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
@RequiredArgsConstructor
public class GoogleOidcProvider implements OidcProvider {

    private final GoogleIdTokenVerifier googleIdTokenVerifier;
    private final OAuthProperties oAuthProperties;

    @Override
    public SocialInfo getSocialInfo(String idToken) {
        GoogleIdToken.Payload payload = getGoogleIdToken(idToken).getPayload();
        return new SocialInfo(
                payload.getSubject(),
                SocialPlatform.GOOGLE,
                (String) payload.get("name"),
                (String) payload.get("picture")
        );
    }

    @Override
    public String getIdToken(String authCode) {
        try {
            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    "https://oauth2.googleapis.com/token",
                    oAuthProperties.googleClientId(),
                    oAuthProperties.googleClientSecret(),
                    authCode,
                    oAuthProperties.googleRedirectUrl()
            ).execute();

            String idToken = tokenResponse.getIdToken();
            if (idToken == null || idToken.isEmpty()) { throw new PeautyException(PeautyResponseCode.GOOGLE_AUTH_CLIENT_ERROR);}
            return idToken;

        } catch (Exception e) {
            throw new PeautyException(PeautyResponseCode.GOOGLE_AUTH_CLIENT_ERROR);
        }
    }

    private GoogleIdToken getGoogleIdToken(String idToken) {
        try {
            GoogleIdToken googleIdToken = googleIdTokenVerifier.verify(idToken);
            if (googleIdToken == null) {
                throw new PeautyException(PeautyResponseCode.GOOGLE_AUTH_CLIENT_ERROR);
            }
            return googleIdToken;
        } catch (GeneralSecurityException | IOException e) {
            throw new PeautyException(PeautyResponseCode.GOOGLE_AUTH_CLIENT_ERROR);
        }
    }
}