package com.peauty.customer.implementaion.addy;

import com.peauty.addy.dalle.DalleClient;
import com.peauty.addy.dalle.ImageMaskGenerator;
import com.peauty.customer.business.addy.AddyPort;
import com.peauty.customer.business.addy.dto.CreateMaskingResult;
import com.peauty.domain.addy.AddyImage;
import com.peauty.persistence.addy.AddyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddyAdapter implements AddyPort {

    private final DalleClient dalleClient;
    private final ImageMaskGenerator imageMaskGenerator;
    private final AddyRepository addyRepository;

    @Override
    public CreateMaskingResult sendImageToMasking() {
        return null;
    }

    @Override
    public AddyImage sendImageToDalle(CreateMaskingResult result) {
        return null;
    }
}
