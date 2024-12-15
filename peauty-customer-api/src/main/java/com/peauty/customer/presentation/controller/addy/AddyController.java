package com.peauty.customer.presentation.controller.addy;

import com.peauty.customer.business.addy.AddyService;
import com.peauty.customer.business.addy.dto.CreateAddyImageResult;
import com.peauty.customer.presentation.controller.addy.dto.CreateAddyImageRequest;
import com.peauty.customer.presentation.controller.addy.dto.CreateAddyImageResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class AddyController {

    private final AddyService addyService;

    @PostMapping("/users/{userId}/puppies/{puppyId}/addy")
    public CreateAddyImageResponse createAddyImage(
            @PathVariable Long userId,
            @PathVariable Long puppyId,
            @RequestBody CreateAddyImageRequest request
    ) {
        CreateAddyImageResult createAddyImageResult =
                addyService.createAddyImage(
                        userId, puppyId, request.toCommand());
        return CreateAddyImageResponse.from(createAddyImageResult);
    }

    @PostMapping("/users/{userId}/puppies/{puppyId}/test/maskingImage")
    public CreateAddyImageResponse testGenerateMaskingImage(
            @PathVariable Long userId,
            @PathVariable Long puppyId,
            @RequestBody CreateAddyImageRequest request
    ) {
        CreateAddyImageResult createAddyImageResult =
                addyService.testGenerateMaskingImage(
                        userId, puppyId, request.toCommand());
        return CreateAddyImageResponse.from(createAddyImageResult);
    }

    @PostMapping("/users/{userId}/puppies/{puppyId}/test/dalleImage")
    public CreateAddyImageResponse testGenerateDalleImage(
            @PathVariable Long userId,
            @PathVariable Long puppyId,
            @RequestBody CreateAddyImageRequest request
    ) {
        CreateAddyImageResult createAddyImageResult =
                addyService.testGenerateDalleImage(
                        userId, puppyId, request.toCommand());
        return CreateAddyImageResponse.from(createAddyImageResult);
    }
}
