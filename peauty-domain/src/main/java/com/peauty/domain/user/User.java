package com.peauty.domain.user;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String socialId;
    private SocialPlatform socialPlatform;
    private String name;
    private String nickname;
    private String phoneNum;
    private String profileImageUrl;
    private Status status;
    private Role role;

    public void deactivate() {
        this.status = Status.INACTIVE;
    }

    public void activate() {
        this.status = Status.ACTIVE;
    }
}