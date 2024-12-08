package com.peauty.designer.business.puppy;

import com.peauty.domain.puppy.Puppy;

import java.util.List;

public interface PuppyPort {

    Puppy getPuppyByCustomerIdAndPuppyId(Long userId, Long puppyId);
    Puppy getPuppyByPuppyId(Long puppyId);
    List<Puppy> findAllByCustomerId(Long customerId);
}
