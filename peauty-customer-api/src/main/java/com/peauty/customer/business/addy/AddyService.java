package com.peauty.customer.business.addy;

import com.peauty.customer.business.addy.dto.CreateAddyImageCommand;
import com.peauty.customer.business.addy.dto.CreateAddyImageResult;

public interface AddyService {
    CreateAddyImageResult createAddyImage(
            Long userId, Long puppyId, CreateAddyImageCommand request);

    CreateAddyImageResult testGenerateMaskingImage(
            Long userId, Long puppyId, CreateAddyImageCommand command);

    CreateAddyImageResult testGenerateDalleImage(Long userId, Long puppyId, CreateAddyImageCommand command);
}
