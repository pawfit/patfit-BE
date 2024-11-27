package com.peauty.domain.customer;

import com.peauty.domain.puppy.Puppy;
import com.peauty.domain.user.AuthInfo;
import com.peauty.domain.user.Role;
import com.peauty.domain.user.SocialPlatform;
import com.peauty.domain.user.Status;
import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private Long customerId;
    private String socialId;
    private SocialPlatform socialPlatform;
    private String name;
    private String phoneNumber;
    private Status status;
    private Role role;
    private String nickname;
    private String address;
    private String profileImageUrl;
    private List<Puppy> puppies;

    public AuthInfo getAuthInfo() {
        return new AuthInfo(
                customerId,
                socialId,
                socialPlatform,
                status,
                role
        );
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
}
