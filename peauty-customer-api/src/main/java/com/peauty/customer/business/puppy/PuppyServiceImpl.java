package com.peauty.customer.business.puppy;

import com.peauty.customer.business.puppy.dto.AddPuppyCommand;
import com.peauty.customer.business.puppy.dto.PuppyResult;
import com.peauty.customer.business.puppy.dto.UpdatePuppyCommand;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.puppy.Puppy;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.persistence.puppy.PuppyEntity;
import com.peauty.customer.implementaion.puppy.PuppyMapper;
import com.peauty.persistence.puppy.PuppyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PuppyServiceImpl implements PuppyService {

    private final PuppyPort puppyPort;
    private final PuppyRepository puppyRepository;

    @Override
    @Transactional
    public PuppyResult registerPuppy(AddPuppyCommand command) {

        // 반려견 생성, 저장

        Puppy puppyToSave = command.toDomain()
                                    .assignCustomer(command.userId());// AddPuppyCommand를 도메인 객체로 변환
        Puppy savedPuppy = puppyPort.savePuppy(puppyToSave); // Port로 이어서 저장

        return PuppyResult.from(savedPuppy);
    }

    @Override
    public PuppyResult getPuppy(Long userId, Long puppyId){

        // 반려견 조회
        Puppy puppy = puppyPort.findPuppy(userId, puppyId);
        return PuppyResult.from(puppy);
    }

    @Override
    @Transactional
    public PuppyResult updatePuppy(UpdatePuppyCommand command){

        // 기존 반려견 조회
        PuppyEntity entity = puppyRepository.findByIdAndCustomerId(command.puppyId(), command.userId())
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_PUPPY));

        // 업데이트
        entity.update(
                command.name(),
                command.breed(),
                command.weight(),
                command.sex(),
                command.age(),
                command.birthdate(),
                command.detail(),
                command.disease(),
                command.diseaseDescription(),
                command.profileImageUrl()
        );


        PuppyEntity savedEntity = puppyRepository.save(entity);
        return PuppyResult.from(PuppyMapper.toDomain(savedEntity));
    }

    @Override
    @Transactional
    public void deletePuppy(Long userId, Long puppyId) {

        // 삭제
        puppyPort.deletePuppy(puppyId);
    }





}
