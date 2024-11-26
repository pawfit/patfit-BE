package com.peauty.customer.business.puppy;

import com.peauty.domain.puppy.Puppy;

public interface PuppyPort {
    Puppy savePuppy(Puppy puppy); // 반려견 저장
    Puppy findPuppy(Long userId, Long puppyId); // 특정 반려견 조회
    Puppy updatePuppy(Puppy puppy); // 반려견 정보 수정
    void deletePuppy(Long puppyId); // 반려견 삭제
}
