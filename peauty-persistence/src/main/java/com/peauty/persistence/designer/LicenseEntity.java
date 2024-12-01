package com.peauty.persistence.designer;

import com.peauty.domain.designer.Designer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

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

    @ManyToOne
    @JoinColumn(name = "designer_id", nullable = false)
    private DesignerEntity designer;

}
