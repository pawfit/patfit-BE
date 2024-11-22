package com.peauty.designer.implementation.designer;

import com.peauty.domain.user.Role;
import com.peauty.domain.user.User;
import com.peauty.persistence.customer.CustomerEntity;
import com.peauty.persistence.designer.DesignerEntity;

public class DesignerMapper {

    private DesignerMapper() {
        // private 생성자로 인스턴스화 방지
    }

    public static User toDomain(DesignerEntity designerEntity) {
        return new User(
                designerEntity.getId(),
                designerEntity.getSocialId(),
                designerEntity.getSocialPlatform(),
                designerEntity.getName(),
                designerEntity.getNickname(),
                designerEntity.getPhoneNum(),
                designerEntity.getAddress(),
                designerEntity.getProfileImageUrl(),
                designerEntity.getStatus(),
                Role.ROLE_DESIGNER
        );
    }

    public static DesignerEntity toEntity(User user) {
        return DesignerEntity.builder()
                .id(user.getUserId())
                .socialId(user.getSocialId())
                .socialPlatform(user.getSocialPlatform())
                .name(user.getName())
                .nickname(user.getNickname())
                .phoneNum(user.getPhoneNum())
                .profileImageUrl(user.getProfileImageUrl())
                .status(user.getStatus())
                .build();
    }
}
