package com.peauty.auth.provider;


import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.auth.client.OidcPublicKey;
import com.peauty.auth.client.OidcPublicKeyList;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@Component
public class PublicKeyProvider {

    public PublicKey generatePublicKey(Map<String, String> tokenHeaders, OidcPublicKeyList publicKeys) {
        String kid = tokenHeaders.get("kid");
        String alg = tokenHeaders.get("alg");

        if (kid == null || alg == null) {
            throw new PeautyException(PeautyResponseCode.INTERNAL_SERVER_ERROR);
        }

        OidcPublicKey publicKey = publicKeys.getMatchedKey(kid, alg);
        return getPublicKey(publicKey);
    }

    private PublicKey getPublicKey(OidcPublicKey publicKey) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        byte[] nBytes = decoder.decode(publicKey.n());
        byte[] eBytes = decoder.decode(publicKey.e());

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(
                new BigInteger(1, nBytes),
                new BigInteger(1, eBytes)
        );

        try {
            return KeyFactory.getInstance(publicKey.kty())
                    .generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new PeautyException(PeautyResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}