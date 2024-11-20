package com.peauty.persistence.customer;

import com.peauty.domain.user.Status;
import com.peauty.domain.user.OidcProviderType;
import com.peauty.persistence.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CustomerEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "oidc_provider_id", nullable = false)
    private String oidcProviderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "oidc_provider_type", nullable = false)
    private OidcProviderType oidcProviderType;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "phoneNum", length = 50)
    private String phoneNum;

    @Lob
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    // TODO domain 의 의존성 끊기 고려
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
    // 상태 변경 메서드 예시
    public void deactivate() {
        this.status = Status.INACTIVE;
    }

    public void updateProfile(String nickname, String profileImageUrl) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
