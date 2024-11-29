package com.peauty.domain.designer;

import com.peauty.domain.user.AuthInfo;
import com.peauty.domain.user.Role;
import com.peauty.domain.user.SocialPlatform;
import com.peauty.domain.user.Status;
import lombok.*;

import java.util.Optional;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Designer {

    private Long designerId;
    private String socialId;
    private SocialPlatform socialPlatform;
    private String name;
    private String phoneNumber;
    private String email;
    private Status status;
    private Role role;
    private String nickname;
    private String address;
    private String profileImageUrl;
    private DesignerInfo designerInfo;
    private Shop shop;
    private Rating rating;

    public AuthInfo getAuthInfo() {
        return new AuthInfo(
                designerId,
                socialId,
                socialPlatform,
                status,
                role
        );
    }

    public Optional<DesignerInfo> getDesignerInfoOptional() {
        return Optional.ofNullable(designerInfo);
    }

    public Optional<Shop> getShopOptional() {
        return Optional.ofNullable(shop);
    }

    public void deactivate() {
        this.status = Status.INACTIVE;
    }

    public void activate() {
        this.status = Status.ACTIVE;
    }

    public boolean isActive() {
        return status == Status.ACTIVE;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateBannerImageUrl(String bannerImageUrl) {
        this.profileImageUrl = bannerImageUrl;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

}
