package com.pawfit.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Component
public class WebLoggingFilter extends OncePerRequestFilter {

    private final AntPathMatcher matcher = new AntPathMatcher();
    private final Set<String> excludeUrls = Collections.emptySet();
    private static final String EMPTY = "";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if (isExcludedUrl(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        ReusableRequestWrapper wrappingRequest = new ReusableRequestWrapper(request);
        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);
        String queryString = request.getQueryString();

        log.info(
                "[REQUEST] {}, {}\n Headers: {}\n Body: {}",
                request.getMethod(),
                request.getRequestURI() + (StringUtils.hasText(queryString) ? "?" + queryString : ""),
                getHeaders(request),
                getRequestBody(wrappingRequest)
        );

        filterChain.doFilter(wrappingRequest, wrappingResponse);
        log.info("[RESPONSE] Body : {}", getResponseBody(wrappingResponse));
        wrappingResponse.copyBodyToResponse();
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }

        return headerMap;
    }

    private String getRequestBody(ReusableRequestWrapper requestWrapper) {
        String body = requestWrapper.readBody();
        return org.apache.commons.lang3.StringUtils.normalizeSpace(body);
    }

    private String getResponseBody(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(
                response,
                ContentCachingResponseWrapper.class
        );

        if (wrapper != null) {
            return getBodyString(wrapper.getContentAsByteArray());
        }
        return EMPTY;
    }

    private String getBodyString(byte[] byteArray) {
        if (byteArray != null && byteArray.length > 0) {
            try {
                return new String(byteArray, 0, byteArray.length, StandardCharsets.UTF_8);
            } catch (Exception e) {
                return EMPTY;
            }
        }
        return EMPTY;
    }

    private boolean isExcludedUrl(String url) {
        return excludeUrls.stream()
                .anyMatch(pattern -> matcher.match(pattern, url));
    }
}