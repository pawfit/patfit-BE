package com.peauty.domain.designer;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DesignerInfo {

    private Long designerInfoId;
    private String introduce;
    private String notice;
    private String bannerImageUrl;
    private int yearsOfExperience;

    public void updateIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void updateNotice(String notice) {
        this.notice = notice;
    }

    public void updateBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }

    public void updateYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public static DesignerInfo getFirstDesignerInfo() {
        return DesignerInfo.builder()
                .designerInfoId(0L)
                .build();
    }
}
