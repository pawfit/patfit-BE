package com.peauty.customer.business.customer;

import com.peauty.customer.business.customer.dto.*;
import com.peauty.customer.business.designer.DesignerPort;
import com.peauty.customer.business.internal.InternalPort;
import com.peauty.domain.customer.Customer;
import com.peauty.domain.designer.Badge;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerPort customerPort;
    private final InternalPort internalPort;
    private final DesignerPort designerPort;

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
        // TODO: 추후 updateCustomerProfile을 이용해 한 줄로 코드 변경 예정.
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

    @Override
    public GetDesignerBadgesForCustomerResult getDesignerBadgesByCustomer(Long designerId) {
        // 디자이너가 획득한 뱃지 가져오기
        List<Badge> acquiredBadges = designerPort.getAcquiredBadges(designerId);

        // 대표 뱃지 필터링
        List<Badge> representativeBadges = acquiredBadges.stream()
                .filter(Badge::getIsRepresentativeBadge)
                .toList();

        return GetDesignerBadgesForCustomerResult.from(acquiredBadges, representativeBadges);
    }
}
