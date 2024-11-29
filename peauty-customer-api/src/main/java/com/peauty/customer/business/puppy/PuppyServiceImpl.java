package com.peauty.customer.business.puppy;

import com.peauty.customer.business.internal.InternalPort;
import com.peauty.customer.business.puppy.dto.*;
import com.peauty.domain.puppy.Puppy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PuppyServiceImpl implements PuppyService {

    private final PuppyPort puppyPort;
    private final InternalPort internalPort;

    @Override
    @Transactional
    public UploadPuppyImageResult uploadPuppyImage(Long customerId, Long puppyId, MultipartFile file) {
        Puppy puppy = puppyPort.getByPuppyId(puppyId);
        String uploadedPuppyImageUrl = internalPort.uploadImage(file);
        puppy.updateProfileImageUrl(uploadedPuppyImageUrl);
        return UploadPuppyImageResult.from(puppyPort.save(puppy));
    }

    @Override
    @Transactional
    public RegisterPuppyResult registerPuppy(RegisterPuppyCommand command) {
        Puppy puppyToSave = command.toDomain()
                .assignCustomer(command.userId());
        Puppy savedPuppy = puppyPort.save(puppyToSave);
        return RegisterPuppyResult.from(savedPuppy);
    }

    @Override
    public GetPuppyDetailResult getPuppyDetail(Long userId, Long puppyId){
        Puppy puppy = puppyPort.findPuppy(userId, puppyId);
        return GetPuppyDetailResult.from(puppy);
    }

    @Override
    @Transactional
    public UpdatePuppyDetailResult updatePuppyDetail(Long userId, Long puppyId, UpdatePuppyDetailCommand command){
        Puppy puppyToupdate = puppyPort.findPuppy (userId, puppyId);
        puppyToupdate.updateName(command.name());
        puppyToupdate.updateBreed(command.breed());
        puppyToupdate.updateWeight(command.weight());
        puppyToupdate.updateSex(command.sex());
        puppyToupdate.updateAge(command.age());
        puppyToupdate.updateBirthdate(command.birthdate());
        puppyToupdate.updateDetail(command.detail());
        puppyToupdate.updateDisease(command.disease());
        puppyToupdate.updateDiseaseDescription(command.diseaseDescription());
        puppyToupdate.updateProfileImageUrl(command.profileImageUrl());
        puppyToupdate.updatePuppySize(command.puppySize());
        Puppy updatedPuppy = puppyPort.save(puppyToupdate);
        return UpdatePuppyDetailResult.from(updatedPuppy);
    }

    @Override
    @Transactional
    public void deletePuppy(Long userId, Long puppyId) {

        // 삭제
        puppyPort.deletePuppy(puppyId);
    }

}
