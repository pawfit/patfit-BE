package com.peauty.customer.business.addy;

import com.peauty.customer.business.addy.dto.CreateMaskingResult;
import com.peauty.domain.addy.AddyImage;

public interface AddyPort {
    CreateMaskingResult sendImageToMasking();
    AddyImage sendImageToDalle(CreateMaskingResult result);
}
