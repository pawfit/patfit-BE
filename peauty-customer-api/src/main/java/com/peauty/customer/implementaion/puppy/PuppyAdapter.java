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
    public Puppy savePuppy(Puppy puppy) {
        // CustomerEntity 조회
        CustomerEntity customer = customerRepository.findById(puppy.getCustomerId())
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_USER));

        // 도메인 객체를 엔티티로 변환 후 저장
        PuppyEntity entity = PuppyMapper.toEntity(puppy, customer); // Domain -> Entity로 변경
        PuppyEntity savedEntity = puppyRepository.save(entity); // 저장

//        PuppyEntity savedEntity = puppyRepository.save(PuppyMapper.toEntity(puppy));
        // 저장된 엔티티를 도메인 객체로 변환 후 반환
        return PuppyMapper.toDomain(savedEntity); // Entity -> Domain 변환 후 반환
    }

    @Override
    public Puppy findPuppy(Long userId, Long puppyId) {
        // 반려견 엔티티 조회
        PuppyEntity puppyEntity = puppyRepository.findByIdAndCustomerId(puppyId, userId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_PUPPY));
        // 조회된 엔티티를 도메인 객체로 변환 후 반환
        return PuppyMapper.toDomain(puppyEntity);
    }

    @Override
    public Puppy updatePuppy(Puppy puppy) {

        // CustomerEntity 조회
        CustomerEntity customer = customerRepository.findById(puppy.getCustomerId())
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_USER));

        // PuppyEntity로 변환
        PuppyEntity entity = puppyRepository.findById(puppy.getPuppyId())
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_PUPPY));

        // 업데이트
        entity.update(
                puppy.getName(),
                puppy.getBreed(),
                puppy.getWeight(),
                puppy.getSex(),
                puppy.getAge(),
                puppy.getBirthdate(),
                puppy.getDetail(),
                puppy.getDisease(),
                puppy.getDiseaseDescription(),
                puppy.getProfileImageUrl()
        );
        entity.assignCustomer(customer);


        return PuppyMapper.toDomain(puppyRepository.save(entity));

/*        // 도메인 객체를 엔티티로 변환 후 저장
        PuppyEntity updatedEntity = puppyRepository.save(PuppyMapper.toEntity(puppy));
        // 저장된 엔티티를 도메인 객체로 변환 후 반환
        return PuppyMapper.toDomain(updatedEntity);*/
    }

    @Override
    public void deletePuppy(Long puppyId) {
        // 반려견 엔티티 조회 및 삭제
        if (!puppyRepository.existsById(puppyId)) {
            throw new PeautyException(PeautyResponseCode.NOT_FOUND_PUPPY);
        }
        puppyRepository.deleteById(puppyId);
    }
}