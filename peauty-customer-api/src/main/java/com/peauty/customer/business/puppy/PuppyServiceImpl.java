package com.peauty.customer.business.puppy;

import com.peauty.customer.business.customer.CustomerPort;
import com.peauty.customer.business.internal.InternalPort;
import com.peauty.customer.business.puppy.dto.*;
import com.peauty.domain.customer.Customer;
import com.peauty.domain.puppy.Puppy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PuppyServiceImpl implements PuppyService {

    private final PuppyPort puppyPort;
    private final InternalPort internalPort;
    private final CustomerPort customerPort;

    @Override
    @Transactional
    public UploadPuppyImageResult uploadPuppyImage(Long customerId, Long puppyId, MultipartFile file) {
        Puppy puppyImageToUpload = puppyPort.getPuppyByPuppyId(puppyId);
        String uploadedPuppyImageUrl = internalPort.uploadImage(file);
        puppyImageToUpload.updateProfileImageUrl(uploadedPuppyImageUrl);
        return UploadPuppyImageResult.from(puppyPort.save(puppyImageToUpload));
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
        Puppy puppy = puppyPort.getPuppyByCustomerIdAndPuppyId(userId, puppyId);
        return GetPuppyDetailResult.from(puppy);
    }

    @Override
    @Transactional
    public UpdatePuppyDetailResult updatePuppyDetail(Long userId, Long puppyId, UpdatePuppyDetailCommand command){
        Puppy puppyToUpdate = puppyPort.getPuppyByCustomerIdAndPuppyId(userId, puppyId);
        // TODO: puppyToUpdate.updatePuppyDetail 방식으로 한 줄 수정 예정
        puppyToUpdate.updateName(command.name());
        puppyToUpdate.updateBreed(command.breed());
        puppyToUpdate.updateWeight(command.weight());
        puppyToUpdate.updateSex(command.sex());
        puppyToUpdate.updateBirthdate(command.birthdate());
        puppyToUpdate.updateDetail(command.detail());
        puppyToUpdate.updateDisease(command.disease());
        puppyToUpdate.updateDiseaseDescription(command.diseaseDescription());
        puppyToUpdate.updateProfileImageUrl(command.profileImageUrl());
        puppyToUpdate.updatePuppySize(command.puppySize());

        Puppy updatedPuppy = puppyPort.save(puppyToUpdate);
        return UpdatePuppyDetailResult.from(updatedPuppy);
    }

    @Override
    @Transactional
    public void deletePuppy(Long userId, Long puppyId) {
        // 삭제
        puppyPort.deletePuppy(puppyId);
    }

    @Override
    public GetPuppyProfilesResult getPuppyProfiles(Long customerId) {
        Customer customer = customerPort.getCustomerById(customerId);
        List<Puppy> puppies = puppyPort.findAllByCustomerId(customerId);
        return GetPuppyProfilesResult.from(customer, puppies);
    }

}



