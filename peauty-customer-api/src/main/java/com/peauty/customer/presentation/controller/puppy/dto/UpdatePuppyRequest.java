package com.peauty.customer.presentation.controller.puppy.dto;

import com.peauty.customer.business.puppy.dto.UpdatePuppyCommand;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.puppy.Breed;
import com.peauty.domain.puppy.Disease;
import com.peauty.domain.puppy.Sex;
import com.peauty.domain.response.PeautyResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record UpdatePuppyRequest(
        @Schema(description = "반려견 이름", example = "꾸미")
        @NotEmpty
        String name,

        @Schema(description = "반려견 품종", example = "RETRIEVER")
        @NotNull
        Breed breed,

        @Schema(description = "반려견 무게(kg)", example = "10")
        @NotNull
        Long weight,

        @Schema(description = "반려견 성별", example = "M")
        @NotNull
        Sex sex,

        @Schema(description = "반려견 나이", example = "3")
        @NotNull
        Integer age,

        @Schema(description = "반려견 생년월일", example = "2021-11-26")
        @NotNull
        LocalDate birthdate,

        @Schema(description = "특이사항", example = "잘 짖어요")
        String detail,

        @Schema(description = "질병 목록", example = "[\"FLEAS\", \"KENNEL_COUGH\"]")
        List<Disease> disease,

        String diseaseDescription,

        String profileImageUrl
) {
    public UpdatePuppyCommand toCommand(Long userId, Long puppyId) {
        if (puppyId == null) {
            throw new PeautyException(PeautyResponseCode.NOT_FOUND_PUPPY);
        }
        return new UpdatePuppyCommand(
                userId,
                puppyId,
                name,
                breed,
                sex,
                weight,
                age,
                birthdate,
                detail,
                disease,
                diseaseDescription,
                profileImageUrl
        );
    }
}