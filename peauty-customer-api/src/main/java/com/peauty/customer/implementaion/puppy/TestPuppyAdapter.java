/*
package com.peauty.customer.implementaion.puppy;

import com.peauty.customer.business.puppy.PuppyPort;
import com.peauty.customer.presentation.controller.puppy.dto.AddPuppyRequest;
import com.peauty.customer.presentation.controller.puppy.dto.PuppyDetailResponse;
import com.peauty.customer.presentation.controller.puppy.dto.UpdatePuppyRequest;
import com.peauty.customer.presentation.controller.puppy.dto.UpdatePuppyResponse;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.puppy.Puppy;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.persistence.puppy.PuppyEntity;
import com.peauty.persistence.puppy.PuppyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestPuppyAdapter implements PuppyPort {

    private final PuppyRepository puppyRepository;

    @Override
    public Puppy registerPuppy(Long userId, AddPuppyRequest req){
        // 도메인 객체 생성
        Puppy puppyToSave = Puppy.builder()
                .name(req.name())
                .breed(req.breed())
                .weight(req.weight())
                .sex(req.sex())
                .age(req.age())
                .birthdate(req.birthdate())
                .detail(req.detail())
                .disease(req.disease())
                .diseaseDescription(req.diseaseDescription())
                .profileImageUrl(req.profileImageUrl())
                .build();

        // 도메인 객체를 엔티티로 변환 후 저장
        PuppyEntity savedEntity = puppyRepository.save(PuppyMapper.toEntity(puppyToSave));
        return PuppyMapper.toDomain(savedEntity); // 저장된 엔티티를 도메인 객체로 변환

    }

    @Override
    public PuppyDetailResponse getPuppyDetail(Long userId, Long puppyId) {
        // 반려견 엔티티 조회
        PuppyEntity puppyEntity = puppyRepository.findByIdAndCustomerId(puppyId, userId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_PUPPY));

        // PuppyDetailResp로 변환하여 반환
        return new PuppyDetailResponse(
                puppyEntity.getId(),
                puppyEntity.getName(),
                puppyEntity.getBreed().toString(),
                puppyEntity.getWeight(),
                puppyEntity.getSex().toString(),
                puppyEntity.getAge(),
                puppyEntity.getBirthdate(),
                puppyEntity.getDetail(),
                puppyEntity.getDisease().stream().map(Enum::toString).toList(),
                puppyEntity.getDiseaseDescription(),
                puppyEntity.getProfileImageUrl()
        );
    }

    @Override
    public UpdatePuppyResponse updatePuppy(Long userId, Long puppyId, UpdatePuppyRequest req) {
        // 반려견 엔티티 조회
        PuppyEntity puppyEntity = puppyRepository.findByIdAndCustomerId(puppyId, userId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_PUPPY));

        // 업데이트된 엔티티 생성
        PuppyEntity updatedEntity = puppyEntity.update(
                req.name(),
                req.breed(),
                req.weight(),
                req.sex(),
                req.age(),
                req.birthdate(),
                req.detail(),
                req.disease(),
                req.diseaseDescription(),
                req.profileImageUrl()
        );

        // 변경된 엔티티 저장
        updatedEntity = puppyRepository.save(updatedEntity);

        // 응답 변환 및 반환
        return new UpdatePuppyResponse(
                updatedEntity.getId(),
                updatedEntity.getName(),
                updatedEntity.getBreed().toString(),
                updatedEntity.getWeight(),
                updatedEntity.getSex().toString(),
                updatedEntity.getAge(),
                updatedEntity.getBirthdate(),
                updatedEntity.getDetail(),
                updatedEntity.getDisease().stream().map(Enum::toString).toList(),
                updatedEntity.getDiseaseDescription(),
                updatedEntity.getProfileImageUrl()
        );
    }

    @Override
    public void deletePuppy(Long userId, Long puppyId) {
        // 반려견 소유 여부 검증
        var puppyEntity = puppyRepository.findByIdAndCustomerId(puppyId, userId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_PUPPY));

        // 삭제 처리
        puppyRepository.delete(puppyEntity);
    }





}
*/
