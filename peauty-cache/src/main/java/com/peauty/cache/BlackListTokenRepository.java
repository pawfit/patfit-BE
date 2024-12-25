package com.peauty.cache;

import org.springframework.stereotype.Component;

@Component
public class BlackListTokenRepository {

    // TODO 로그아웃한 토큰을 캐싱
    public boolean isBlackListToken(String token) {
        return false;
    }
}
