package com.peauty.designer.implementation.designer;

import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.DesignerInfo;
import com.peauty.domain.designer.Shop;
import com.peauty.domain.user.Role;
import com.peauty.persistence.designer.DesignerEntity;
import com.peauty.persistence.designer.DesignerInfoEntity;
import com.peauty.persistence.designer.ShopEntity;

public class DesignerMapper {

    private DesignerMapper() {
        // private 생성자로 인스턴스화 방지
    }

    public static Designer toDomain(DesignerEntity designerEntity) {
        return Designer.builder()
                .designerId(designerEntity.getId())
                .socialId(designerEntity.getSocialId())
                .socialPlatform(designerEntity.getSocialPlatform())
                .name(designerEntity.getName())
                .phoneNumber(designerEntity.getPhoneNum())
                .email(designerEntity.getEmail())
                .status(designerEntity.getStatus())
                .role(Role.ROLE_DESIGNER)
                .nickname(designerEntity.getNickname())
                .address(designerEntity.getAddress())
                .profileImageUrl(designerEntity.getProfileImageUrl())
                .designerInfo(designerEntity.getDesignerInfo() != null
                        ? DesignerInfo.builder()
                        .designerInfoId(designerEntity.getDesignerInfo().getId())
                        .introduce(designerEntity.getDesignerInfo().getIntroduce())
                        .notice(designerEntity.getDesignerInfo().getNotice())
                        .bannerImageUrl(designerEntity.getDesignerInfo().getBannerImageUrl())
                        .yearsOfExperience(designerEntity.getDesignerInfo().getYearsOfExperience())
                        .build()
                        : null)
                .shop(designerEntity.getShop() != null
                        ? Shop.builder()
                        .shopId(designerEntity.getShop().getId())
                        .shopName(designerEntity.getShop().getShopName())
                        .directionGuide(designerEntity.getShop().getDirectionGuide())
                        .openHours(designerEntity.getShop().getOpenHours())
                        .paymentOptions(designerEntity.getShop().getPaymentOptions())
                        .build()
                        : null)
                .build();
    }

    public static DesignerEntity toDesignerEntity(Designer designer) {
        return DesignerEntity.builder()
                .id(designer.getDesignerId())
                .socialId(designer.getSocialId())
                .socialPlatform(designer.getSocialPlatform())
                .name(designer.getName())
                .phoneNum(designer.getPhoneNumber())
                .email(designer.getEmail())
                .status(designer.getStatus())
                .nickname(designer.getNickname())
                .address(designer.getAddress())
                .profileImageUrl(designer.getProfileImageUrl())
                .designerInfo(designer.getDesignerInfoOptional()
                        .map(info -> DesignerInfoEntity.builder()
                                .id(info.getDesignerInfoId())
                                .introduce(info.getIntroduce())
                                .notice(info.getNotice())
                                .bannerImageUrl(info.getBannerImageUrl())
                                .yearsOfExperience(info.getYearsOfExperience())
                                .build())
                        .orElse(null))
                .shop(designer.getShopOptional()
                        .map(shop -> ShopEntity.builder()
                                .id(shop.getShopId())
                                .shopName(shop.getShopName())
                                .directionGuide(shop.getDirectionGuide())
                                .openHours(shop.getOpenHours())
                                .paymentOptions(shop.getPaymentOptions())
                                .build())
                        .orElse(null))
                .build();
    }
}
