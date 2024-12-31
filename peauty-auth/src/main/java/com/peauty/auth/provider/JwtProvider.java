package com.peauty.auth.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peauty.auth.properties.OAuthUserDetails;
import com.peauty.cache.BlackListTokenRepository;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.token.SignTokens;
import com.peauty.domain.user.AuthInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.PublicKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.access-token-expiration}")
    private Long accessTokenExpiration;
    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;
    private final BlackListTokenRepository blackListTokenRepository;
    private final Key key;
    private final ObjectMapper jacksonObjectMapper;

    public SignTokens generateToken(AuthInfo authInfo) {
        try {
            String accessToken = Jwts.builder()
                    .claim("user", authInfo)
                    .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();

            String refreshToken = Jwts.builder()
                    .claim("user", authInfo)
                    .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();

            return new SignTokens(accessToken, refreshToken);
        } catch (InvalidKeyException e) {
            throw new PeautyException(PeautyResponseCode.INVALID_KEY);
        }
    }

    public Authentication getAuthentication(String token) {
        AuthInfo authInfo = getAuthInfo(token);
        OAuthUserDetails oAuthUserDetails = new OAuthUserDetails(authInfo);
        return new UsernamePasswordAuthenticationToken(oAuthUserDetails, "", oAuthUserDetails.getAuthorities());
    }

    public AuthInfo getAuthInfo(String token) {
        Object parseAuthInfo = parseClaims(token).get("user");
        if (parseAuthInfo == null) {
            throw new PeautyException(PeautyResponseCode.EMPTY_AUTH_JWT);
        }
        return jacksonObjectMapper.convertValue(parseAuthInfo, AuthInfo.class);
    }

    public Long getTokenExpiredSecond(String token) {
        Claims claims = parseClaims(token);
        return claims.getExpiration().getTime();
    }

    public Claims parseClaims(String token) {
        boolean blackListToken = blackListTokenRepository.isBlackListToken(token);
        if (blackListToken) {
            throw new PeautyException(PeautyResponseCode.BLACK_LIST_TOKEN);
        }
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new PeautyException(PeautyResponseCode.EXPIRED_JWT_TOKEN);
        } catch (RuntimeException e) {
            throw new PeautyException(PeautyResponseCode.WRONG_JWT_TOKEN);
        }
    }


    public Claims parseClaims(String token, PublicKey publicKey) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new PeautyException(PeautyResponseCode.EXPIRED_JWT_TOKEN);
        } catch (RuntimeException e) {
            throw new PeautyException(PeautyResponseCode.WRONG_JWT_TOKEN);
        }
    }
}