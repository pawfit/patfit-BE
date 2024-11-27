package com.peauty.persistence.designer;

import com.peauty.domain.user.SocialPlatform;
import com.peauty.domain.user.Status;
import com.peauty.persistence.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "designer")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DesignerEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "social_id", nullable = false)
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_platform", nullable = false)
    private SocialPlatform socialPlatform;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "phoneNum", length = 50)
    private String phoneNum;

    @Column(name = "address")
    private String address;

    @Lob
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    // TODO domain 의 의존성 끊기 고려
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @OneToOne(mappedBy = "designer", cascade = CascadeType.ALL)
    private DesignerInfoEntity designerInfo;

    @OneToOne(mappedBy = "designer", cascade = CascadeType.ALL)
    private ShopEntity shop;
}

