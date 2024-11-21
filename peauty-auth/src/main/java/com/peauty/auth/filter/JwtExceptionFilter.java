package com.peauty.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyApiResponse;
import com.peauty.domain.response.PeautyResponseCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (PeautyException exception) {
            setErrorResponse(exception.getResponseCode(), response);
        }
    }

    private void setErrorResponse(PeautyResponseCode responseCode, HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json; charset=UTF-8");

        PeautyApiResponse<String> result = PeautyApiResponse.error(responseCode);

        try {
            response.getWriter().write(toJson(result));
        } catch (IOException e) {
            // ignored
        }
    }

    private String toJson(Object data) {
        try {
            return new ObjectMapper().writeValueAsString(data);
        } catch (IOException e) {
            return "{}"; // 기본값 반환 또는 예외 처리
        }
    }
}
