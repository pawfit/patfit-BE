package com.peauty.customer.presentation.controller.puppy.dto;

import com.peauty.customer.business.puppy.dto.AddPuppyCommand;
import com.peauty.domain.puppy.Breed;
import com.peauty.domain.puppy.Disease;
import com.peauty.domain.puppy.PuppySize;
import com.peauty.domain.puppy.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record AddPuppyRequest(
          @Schema(description = "반려견 이름", example = "꼬미")
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
          @Schema(description = "특이사항", example = "잘 짖지 않아요")
          String detail,
          @Schema(description = "질병 목록", example = "[\"FLEAS\", \"KENNEL_COUGH\"]")
          List<Disease> disease,
          @Schema(description = "기타 질병 설명", example = "심하게 흥분하면 기침을 합니다")
          String diseaseDescription,

          String profileImageUrl,
          @Schema(description = "분류", example = "LARGE")
          PuppySize puppySize
) {
    public AddPuppyCommand toCommand(Long userId) {
        return new AddPuppyCommand(
                userId,
                name,
                breed,
                weight,
                sex,
                age,
                birthdate,
                detail,
                disease,
                diseaseDescription,
                profileImageUrl,
                puppySize
        );
    }
}