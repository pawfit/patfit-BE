package com.peauty.designer.business.designer.dto;

public record UpdateDesignerAccountCommand(
        String name,
        String nickname,
        String phoneNumber,
        String address,
        String profileImageUrl,
        String email
) {
}
