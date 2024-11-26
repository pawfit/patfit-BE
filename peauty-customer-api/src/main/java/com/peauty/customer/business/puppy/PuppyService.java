package com.peauty.customer.business.puppy;

import com.peauty.customer.business.puppy.dto.AddPuppyCommand;
import com.peauty.customer.business.puppy.dto.PuppyResult;
import com.peauty.customer.business.puppy.dto.UpdatePuppyCommand;

public interface PuppyService {
    PuppyResult registerPuppy(AddPuppyCommand command);
    PuppyResult getPuppy(Long userId, Long puppyId);
    PuppyResult updatePuppy(UpdatePuppyCommand command);
    void deletePuppy(Long userId, Long puppyId);
}



