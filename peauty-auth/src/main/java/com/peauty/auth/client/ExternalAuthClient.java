package com.peauty.auth.client;

public interface ExternalAuthClient {
    OidcPublicKeyList getPublicKeys();
}