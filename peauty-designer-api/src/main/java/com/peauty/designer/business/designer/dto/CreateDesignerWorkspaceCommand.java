package com.peauty.designer.business.designer.dto;

import com.peauty.domain.designer.License;
import com.peauty.domain.designer.PaymentOption;
import com.peauty.domain.designer.Workspace;

import java.util.List;

public record CreateDesignerWorkspaceCommand(
        String bannerImageUrl,
        String workspaceName,
        String introduceTitle,
        String introduce,
        String noticeTitle,
        String notice,
        String address,
        String addressDetail,
        Integer yearOfExperience,
        List<String> licenses,
        List<PaymentOption> paymentOptions,
        String openHours,
        String closeHours,
        String openDays,
        String directionGuide
) {
    public static Workspace toWorkspace(CreateDesignerWorkspaceCommand command) {
        return Workspace.builder()
                .bannerImageUrl(command.bannerImageUrl())
                .workspaceName(command.workspaceName())
                .introduceTitle(command.introduceTitle())
                .introduce(command.introduce())
                .noticeTitle(command.noticeTitle())
                .notice(command.notice())
                .address(command.address())
                .addressDetail(command.addressDetail())
                .paymentOptions(command.paymentOptions())
                .openHours(command.openHours())
                .closeHours(command.closeHours())
                .openDays(command.openDays())
                .directionGuide(command.directionGuide())
                .build();
    }

    public static List<License> toLicense(CreateDesignerWorkspaceCommand command) {
        return command.licenses().stream()
                .map(imageUrl -> License.builder()
                        .licenseImageUrl(imageUrl)
                        .build())
                .toList();
    }

}
