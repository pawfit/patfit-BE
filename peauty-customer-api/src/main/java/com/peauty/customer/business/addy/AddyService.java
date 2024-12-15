package com.peauty.customer.business.addy;

import com.peauty.customer.business.addy.dto.CreateAddyImageCommand;
import com.peauty.customer.business.addy.dto.CreateAddyImageResult;
import com.peauty.customer.presentation.controller.Addy.dto.CreateAddyImageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

public interface AddyService {
    CreateAddyImageResult createAddyImage(
            Long userId, Long puppyId, CreateAddyImageCommand request);
}
