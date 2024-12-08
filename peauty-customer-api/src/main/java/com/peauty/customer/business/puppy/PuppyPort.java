package com.peauty.customer.business.puppy;

import com.peauty.domain.puppy.Puppy;

import java.util.List;

public interface PuppyPort {
    Puppy getPuppyByCustomerIdAndPuppyId(Long userId, Long puppyId); // 특정 반려견 조회

    void deletePuppy(Long puppyId); // 반려견 삭제

    Puppy getPuppyByPuppyId(Long puppyId);

    Puppy save(Puppy puppy);

    List<Puppy> findAllByCustomerId(Long customerId); // 전체 반려견 조회

    void verifyPuppyOwnership(Long puppyId, Long customerId);
}
