package com.peauty.persistence.designer;

import com.peauty.domain.designer.LicenseVerification;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "license")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LicenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "designer_id", nullable = false)
    private Long designerId;

    @Lob
    @Column(name = "license_image_url", nullable = false)
    private String licenseImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "license_verification", nullable = false)
    private LicenseVerification licenseVerification;

}
