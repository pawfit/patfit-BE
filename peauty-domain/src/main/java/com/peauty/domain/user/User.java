package com.peauty.domain.user;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long userId;
    private String socialId;
    private SocialPlatform socialPlatform;
    private String name;
    private String nickname;
    private String phoneNum;
    private String address;
    private String profileImageUrl;
    private Status status;
    private Role role;

    public void deactivate() {
        this.status = Status.INACTIVE;
    }

    public void activate() {
        this.status = Status.ACTIVE;
    }

    public boolean isActive() {
        return status == Status.ACTIVE;
    }

    public void uploadProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateUserProfile(String name) {
        this.name = name;
    }

    public UserProfile getUserProfile() {
        return UserProfile.builder()
                .userId(this.getUserId())
                .name(this.name)
                .nickname(this.getNickname())
                .profileImageUrl(this.getProfileImageUrl())
                .address(this.getAddress())
                .build();
    }
}