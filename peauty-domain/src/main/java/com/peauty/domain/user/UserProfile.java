package com.peauty.domain.user;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private Long userId;
    private String name;
    private String nickname;
    private String phoneNum;
    private String address;
    private String profileImageUrl;
}
