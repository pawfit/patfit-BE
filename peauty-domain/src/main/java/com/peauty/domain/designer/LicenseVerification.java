package com.peauty.domain.designer;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum LicenseVerification {
    COMPLETE("검증 완료"),
    INCOMPLETE("검증 미완료");

    private final String verifiCation;

    LicenseVerification(String verifiCation) {
        this.verifiCation = verifiCation;
    }
    public static LicenseVerification from(String verifiCation) {
        return Arrays.stream(LicenseVerification.values())
                .filter(it -> it.verifiCation.equalsIgnoreCase(verifiCation))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.INVALID_LICENSE_VERIFICATION_OPTION));
    }

}
