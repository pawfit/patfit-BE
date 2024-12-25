package com.peauty.domain.designer;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum LicenseVerification {
    COMPLETE("검증 완료"),
    INCOMPLETE("검증 미완료");

    private final String value;

    LicenseVerification(String value) {
        this.value = value;
    }
    public static LicenseVerification from(String verification) {
        return Arrays.stream(LicenseVerification.values())
                .filter(it -> it.value.equalsIgnoreCase(verification))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.INVALID_LICENSE_VERIFICATION_OPTION));
    }

}
