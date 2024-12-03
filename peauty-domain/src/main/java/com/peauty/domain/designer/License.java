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

    public void updateLicenseImageUrl(String licenseImageUrl) {
        this.licenseImageUrl = licenseImageUrl;
    }
}
