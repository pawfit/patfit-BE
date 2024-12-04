package com.peauty.customer.implementaion.customer;

import com.peauty.customer.business.auth.dto.SignUpCommand;
import com.peauty.customer.business.customer.CustomerPort;
import com.peauty.domain.customer.Customer;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.Workspace;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.user.Role;
import com.peauty.domain.user.Status;
import com.peauty.persistence.customer.CustomerEntity;
import com.peauty.persistence.customer.CustomerRepository;
import com.peauty.persistence.designer.DesignerRepository;
import com.peauty.persistence.designer.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerAdapter implements CustomerPort {

    private final CustomerRepository customerRepository;
    private final DesignerRepository designerRepository;
    private final WorkspaceRepository workspaceRepository;

    @Override
    public void checkCustomerSocialIdDuplicated(String socialId) {
        if (customerRepository.existsBySocialId(socialId)) {
            throw new PeautyException(PeautyResponseCode.ALREADY_EXIST_USER);
        }
    }

    @Override
    public void checkCustomerNicknameDuplicated(String nickname) {
        if (customerRepository.existsByNickname(nickname)) {
            throw new PeautyException(PeautyResponseCode.ALREADY_EXIST_NICKNAME);
        }
    }

    @Override
    public void checkCustomerPhoneNumDuplicated(String phoneNum) {
        if (customerRepository.existsByPhoneNum(phoneNum)) {
            throw new PeautyException(PeautyResponseCode.ALREADY_EXIST_PHONE_NUM);
        }
    }

    @Override
    public Optional<Customer> findBySocialId(String socialId) {
        return Optional.ofNullable(customerRepository.findBySocialId(socialId))
                .orElse(Optional.empty())
                .map(CustomerMapper::toDomain);
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity customerEntityToSave = CustomerMapper.toEntity(customer);
        CustomerEntity savedCustomerEntity = customerRepository.save(customerEntityToSave);
        return CustomerMapper.toDomain(savedCustomerEntity);
    }

    @Override
    public Customer registerNewCustomer(SignUpCommand command) {
        Customer customerToSave = Customer.builder()
                .customerId(0L)
                .socialId(command.socialId())
                .socialPlatform(command.socialPlatform())
                .name(command.name())
                .nickname(command.nickname())
                .phoneNumber(command.phoneNumber())
                .address(command.address())
                .profileImageUrl(command.profileImageUrl())
                .status(Status.ACTIVE)
                .role(Role.ROLE_CUSTOMER)
                .build();
        return save(customerToSave);
    }

    @Override
    public Customer getByCustomerIdWithoutPuppies(Long customerId) {
        return customerRepository.findById(customerId)
                .map(CustomerMapper::toDomain)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_USER));
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .map(CustomerMapper::toDomain)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_USER));
    }

    @Override
    public List<Workspace> findAllWorkspaceByAddress(String baseAddress) {
        return workspaceRepository.findByBaseAddress(baseAddress)
                .stream()
                .map(CustomerMapper::toWorkspaceDomain)
                .toList();
    }

    @Override
    public Designer findDesignerById(Long designerId) {
        return designerRepository.findById(designerId)
                .map(CustomerMapper::toDesignerDomain)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_DESIGNER));
    }

}