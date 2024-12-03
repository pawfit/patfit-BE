package com.peauty.customer.business.puppy;

import com.peauty.customer.business.puppy.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PuppyService {
    UploadPuppyImageResult uploadPuppyImage(Long customerId, Long puppyId, MultipartFile file);
    void deletePuppy(Long userId, Long puppyId);
    RegisterPuppyResult registerPuppy(RegisterPuppyCommand command);
    GetPuppyDetailResult getPuppyDetail(Long userId, Long puppyId);
    UpdatePuppyDetailResult updatePuppyDetail(Long customerId, Long puppyId, UpdatePuppyDetailCommand command);
    GetPuppyProfilesResult getPuppyProfiles(Long customerId);

}



