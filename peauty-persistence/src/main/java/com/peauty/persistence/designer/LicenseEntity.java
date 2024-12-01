package com.peauty.persistence.designer;

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

    @Lob
    @Column(name = "license_image_url")
    private String licenseImageUrl;
}
