package com.peauty.customer.implementaion.puppy;

import com.peauty.customer.business.puppy.PuppyPort;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.puppy.Puppy;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.persistence.customer.CustomerEntity;
import com.peauty.persistence.customer.CustomerRepository;
import com.peauty.persistence.puppy.PuppyEntity;
import com.peauty.persistence.puppy.PuppyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PuppyAdapter implements PuppyPort {

    private final PuppyRepository puppyRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Puppy findPuppy(Long userId, Long puppyId) {
        // 반려견 엔티티 조회
        PuppyEntity puppyEntity = puppyRepository.findByIdAndCustomerId(puppyId, userId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_PUPPY));
        // 조회된 엔티티를 도메인 객체로 변환 후 반환
        return PuppyMapper.toDomain(puppyEntity);
    }
    @Override
    public void deletePuppy(Long puppyId) {
        // 반려견 엔티티 조회 및 삭제
        if (!puppyRepository.existsById(puppyId)) {
            throw new PeautyException(PeautyResponseCode.NOT_FOUND_PUPPY);
        }
        puppyRepository.deleteById(puppyId);
    }



    @Override
    public Puppy getByPuppyId(Long puppyId){
        return puppyRepository.findById(puppyId)
                .map(PuppyMapper::toDomain)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_PUPPY));
    }

    @Override
    public Puppy save(Puppy puppy){
        CustomerEntity customer = customerRepository.findById(puppy.getCustomerId())
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_USER));
        PuppyEntity puppyEntityToSave = PuppyMapper.toEntity(puppy, customer);
        PuppyEntity savedPuppyEntity = puppyRepository.save(puppyEntityToSave);
        return PuppyMapper.toDomain(savedPuppyEntity);
    }


}