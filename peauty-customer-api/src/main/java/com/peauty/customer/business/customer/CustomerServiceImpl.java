package com.peauty.customer.business.customer;

import com.peauty.customer.business.customer.dto.*;
import com.peauty.customer.business.internal.InternalPort;
import com.peauty.domain.user.User;
import com.peauty.domain.user.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerPort customerPort;
    private final InternalPort internalPort;

    @Override
    @Transactional
    public UploadProfileImageResult uploadProfileImage(Long userId, MultipartFile file) {
        User user = customerPort.getByUserId(userId);
        String uploadedProfileImageUrl = internalPort.uploadImage(file);
        user.uploadProfileImageUrl(uploadedProfileImageUrl);
        return UploadProfileImageResult.from(customerPort.save(user));
    }

    @Override
    public GetCustomerProfileResult getCustomerProfile(Long userId) {
        User user = customerPort.getByUserId(userId);
        UserProfile userProfile = user.getUserProfile();
        return GetCustomerProfileResult.from(userProfile);
    }

    @Override
    @Transactional
    public UpdateCustomerProfileResult updateCustomerProfile(Long userId, UpdateCustomerProfileCommand command) {
        User user = customerPort.getByUserId(userId);
        UserProfile userProfileToUpdate = command.toUserProfileToUpdate(userId);
        user.updateUserProfile(userProfileToUpdate);
        User updatedProfileUser = customerPort.save(user);
        return UpdateCustomerProfileResult.from(updatedProfileUser.getUserProfile());
    }

    @Override
    public void checkCustomerNicknameDuplicated(String nickname) {
        customerPort.checkCustomerNicknameDuplicated(nickname);
    }
}
