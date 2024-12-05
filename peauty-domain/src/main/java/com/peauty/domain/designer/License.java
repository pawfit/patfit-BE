package com.peauty.domain.designer;


import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class License {

    private Long licenseId;
    private String licenseImageUrl;
    private LicenseVerification licenseVerification;

    public void updateLicenseImageUrl(String licenseImageUrl) {
        this.licenseImageUrl = licenseImageUrl;
    }
    public void updateLicenseVerification(LicenseVerification licenseVerification) {
        this.licenseVerification = licenseVerification;
    }

}
