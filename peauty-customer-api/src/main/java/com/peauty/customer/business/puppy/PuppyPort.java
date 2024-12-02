package com.peauty.customer.business.puppy;

import com.peauty.domain.puppy.Puppy;

public interface PuppyPort {
    Puppy findPuppy(Long userId, Long puppyId); // 특정 반려견 조회
    void deletePuppy(Long puppyId); // 반려견 삭제
    Puppy getByPuppyId(Long puppyId);
    Puppy save(Puppy puppy);
}
