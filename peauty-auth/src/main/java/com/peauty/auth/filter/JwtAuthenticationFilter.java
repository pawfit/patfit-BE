package com.peauty.auth.filter;

import com.peauty.auth.properties.JwtProperties;
import com.peauty.auth.provider.JwtProvider;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = getTokensFromHeader(request, jwtProperties.accessHeader());

        if (token != null) {
            String accessToken = replaceBearerToBlank(token);
            Authentication authentication = jwtProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokensFromHeader(HttpServletRequest request, String header) {
        return request.getHeader(header);
    }

    private String replaceBearerToBlank(String token) {
        String suffix = jwtProperties.grantType();
        if (!token.startsWith(suffix)) {
            throw new PeautyException(PeautyResponseCode.NOT_EXIST_BEARER_SUFFIX);
        }
        return token.replace(suffix, "");
    }
}