package com.peauty.customer.presentation.controller.puppy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeletePuppyResponse {
    @Schema(description = "응답 코드", example = "0000")
    private final String responseCode;

    @Schema(description = "응답 메시지", example = "OK")
    private final String message;

    @Schema(description = "서비스 메시지", example = "정상 처리되었습니다.")
    private final String serviceMessage;

    public static DeletePuppyResponse success() {
        return new DeletePuppyResponse("0000", "OK", "정상 처리되었습니다.");
    }
}