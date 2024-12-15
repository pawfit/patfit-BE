package com.peauty.customer.business.addy;

import com.peauty.customer.business.addy.dto.CreateAddyImageCommand;
import com.peauty.customer.business.addy.dto.CreateAddyImageResult;
import com.peauty.customer.business.addy.dto.CreateMaskingResult;
import com.peauty.domain.addy.AddyImage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddyServiceImpl implements AddyService {
    private final AddyPort addyPort;

    @Override
    public CreateAddyImageResult createAddyImage(
            Long userId, Long puppyId, CreateAddyImageCommand command) {

        // TODO. userId, puppyId 유효성 검증
        // sendImageToMasking의 경우 어떤 파라미터를 받을지 몰라 일단 받지 않은 걸로 두었습니다.
        // TODO. createMaskingResult말고 객체 모델을 만들어서 해야 하는지 고민이 됩니다.
        CreateMaskingResult createMaskingResult = addyPort.sendImageToMasking();

        AddyImage addyImage = addyPort.sendImageToDalle(createMaskingResult);
        return CreateAddyImageResult.from(addyImage);
    }
}
