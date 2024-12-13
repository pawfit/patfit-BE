package com.peauty.domain.designer;

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
    private String profileImageUrl;
    private Integer yearOfExperience;
    private List<License> licenses;
    private List<Badge> badges;

    @Builder
    public record Profile(
            Long designerId,
            String workspaceName,
            String designerName,
            Integer reviewCount,
            Double reviewRating,
            String profileImageUrl,
            Integer yearOfExperience,
            List<Badge> badges,
            String address,
            Scissors scissors
    ){
    }

    public Profile getProfile(Workspace workspace) {
        return Profile.builder()
                .designerId(designerId)
                .designerName(this.name)
                .badges(this.badges)
                .profileImageUrl(this.profileImageUrl)
                .yearOfExperience(this.yearOfExperience)
                .reviewCount(workspace.getReviewCount())
                .reviewRating(workspace.getReviewRating())
                .workspaceName(workspace.getWorkspaceName())
                .address(workspace.getAddress())
                .scissors(workspace.getRating().getScissors())
                .build();
    }

    public AuthInfo getAuthInfo() {
        return new AuthInfo(
                designerId,
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

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateYearOfExperience(Integer yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    public void updateLicenses(List<License> licenses) {
        this.licenses = licenses;
    }

    public void updateBadges(List<Badge> badges) {
        this.badges = badges;
    }

}
