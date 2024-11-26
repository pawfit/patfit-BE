package com.peauty.customer.business.puppy;

import com.peauty.customer.business.puppy.dto.AddPuppyCommand;
import com.peauty.customer.business.puppy.dto.RegisterPuppyResult;
import com.peauty.customer.business.puppy.dto.UpdatePuppyCommand;
import com.peauty.domain.puppy.Puppy;
import com.peauty.domain.puppy.PuppyProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PuppyServiceImpl implements PuppyService {

    private final PuppyPort puppyPort;

    @Override
    @Transactional
    public RegisterPuppyResult addPuppy(AddPuppyCommand command) {

        // 반려견 생성, 저장

        Puppy puppyToSave = command.toDomain()
                                    .assignCustomer(command.userId());// AddPuppyCommand를 도메인 객체로 변환
        Puppy savedPuppy = puppyPort.savePuppy(puppyToSave); // Port로 이어서 저장

        return RegisterPuppyResult.from(savedPuppy);
    }

    @Override
    public RegisterPuppyResult getPuppy(Long userId, Long puppyId){

        // 반려견 조회
        Puppy puppy = puppyPort.findPuppy(userId, puppyId);
        return RegisterPuppyResult.from(puppy);
    }

    @Override
    @Transactional
    public RegisterPuppyResult updatePuppy(UpdatePuppyCommand command){

        // 기존 반려견 조회 및 업데이트 Port로 호출
        Puppy existingPuppy = puppyPort.findPuppy(command.userId(), command.puppyId());
        // PuppyProfile 생성 및 업데이트
        PuppyProfile profile = command.toPuppyProfile();
        existingPuppy.updatePuppyProfile(profile);

        // 저장 및 결과 반환
        Puppy savedPuppy = puppyPort.updatePuppy(existingPuppy);
        return RegisterPuppyResult.from(savedPuppy);
    }

    @Override
    @Transactional
    public void deletePuppy(Long userId, Long puppyId) {

        // 삭제
        puppyPort.deletePuppy(puppyId);
    }





}
