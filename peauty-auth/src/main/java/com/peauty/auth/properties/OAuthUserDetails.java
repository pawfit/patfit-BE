package com.peauty.auth.properties;


import com.peauty.domain.user.AuthInfo;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OAuthUserDetails implements UserDetails, OidcUser, OAuth2User {

    @Getter
    private final AuthInfo authInfo;
    private final Map<String, Object> attribute;

    public OAuthUserDetails(AuthInfo authInfo, Map<String, Object> attribute) {
        this.authInfo = authInfo;
        this.attribute = attribute;
    }

    public OAuthUserDetails(AuthInfo authInfo) {
        this(authInfo, Collections.singletonMap("id", authInfo.userId()));
    }

    @Override
    public String getName() {
        return authInfo.userId().toString();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attribute;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(authInfo.role().name()));
    }
    @Override
    public String getPassword() {
        return "password";
    }

    @Override
    public String getUsername() {
        return authInfo.userId().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }
}