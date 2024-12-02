package com.peauty.customer.business.customer;

import com.peauty.customer.business.customer.dto.*;
import com.peauty.customer.business.internal.InternalPort;
import com.peauty.domain.customer.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerPort customerPort;
    private final InternalPort internalPort;

    @Override
    @Transactional
    public UploadProfileImageResult uploadProfileImage(Long customerId, MultipartFile file) {
        Customer customer = customerPort.getByCustomerIdWithoutPuppies(customerId);
        String uploadedProfileImageUrl = internalPort.uploadImage(file);
        customer.updateProfileImageUrl(uploadedProfileImageUrl);
        return UploadProfileImageResult.from(customerPort.save(customer));
    }

    @Override
    public GetCustomerProfileResult getCustomerProfile(Long customerId) {
        Customer customer = customerPort.getByCustomerIdWithoutPuppies(customerId);
        return GetCustomerProfileResult.from(customer);
    }

    @Override
    @Transactional
    public UpdateCustomerProfileResult updateCustomerProfile(Long customerId, UpdateCustomerProfileCommand command) {
        Customer customerToUpdate = customerPort.getByCustomerIdWithoutPuppies(customerId);
        customerToUpdate.updateName(command.name());
        customerToUpdate.updatePhoneNumber(command.phoneNumber());
        customerToUpdate.updateNickname(command.nickname());
        customerToUpdate.updateProfileImageUrl(command.profileImageUrl());
        customerToUpdate.updateAddress(command.address());
        Customer updatedCustomer = customerPort.save(customerToUpdate);
        return UpdateCustomerProfileResult.from(updatedCustomer);
    }

    @Override
    public void checkCustomerNicknameDuplicated(String nickname) {
        customerPort.checkCustomerNicknameDuplicated(nickname);
    }


}
