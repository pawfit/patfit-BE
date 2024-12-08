package com.peauty.designer.business.puppy;

import com.peauty.domain.puppy.Puppy;

import java.util.List;

public interface PuppyPort {

    Puppy getPuppyByCustomerIdAndPuppyId(Long customerId, Long puppyId);
    Puppy getPuppyByPuppyId(Long puppyId);
    List<Puppy> getAllPuppiesByCustomerId(Long customerId);
}
