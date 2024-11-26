package com.peauty.customer.business.puppy;

import com.peauty.customer.business.puppy.dto.AddPuppyCommand;
import com.peauty.customer.business.puppy.dto.RegisterPuppyResult;
import com.peauty.customer.business.puppy.dto.UpdatePuppyCommand;

public interface PuppyService {
    RegisterPuppyResult addPuppy(AddPuppyCommand command);
    RegisterPuppyResult getPuppy(Long userId, Long puppyId);
    RegisterPuppyResult updatePuppy(UpdatePuppyCommand command);
    void deletePuppy(Long userId, Long puppyId);
}



