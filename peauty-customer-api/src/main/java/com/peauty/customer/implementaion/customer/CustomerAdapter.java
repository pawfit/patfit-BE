package com.peauty.customer.implementaion.customer;

import com.peauty.customer.business.auth.dto.SignUpCommand;
import com.peauty.customer.business.customer.CustomerPort;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.user.Role;
import com.peauty.domain.user.Status;
import com.peauty.domain.user.User;
import com.peauty.persistence.customer.CustomerEntity;
import com.peauty.persistence.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerAdapter implements CustomerPort {

    private final CustomerRepository customerRepository;

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
    public Optional<User> findBySocialId(String socialId) {
        return Optional.ofNullable(customerRepository.findBySocialId(socialId))
                .orElse(Optional.empty())
                .map(CustomerMapper::toDomain);
    }

    @Override
    public User save(User customer) {
        CustomerEntity customerEntityToSave = CustomerMapper.toEntity(customer);
        CustomerEntity savedCustomerEntity = customerRepository.save(customerEntityToSave);
        return CustomerMapper.toDomain(savedCustomerEntity);
    }

    @Override
    public User registerNewCustomer(SignUpCommand command) {
        User userToSave = new User(
                0L,
                command.socialId(),
                command.socialPlatform(),
                command.name(),
                command.nickname(),
                command.phoneNum(),
                command.address(),
                command.profileImageUrl(),
                Status.ACTIVE,
                Role.ROLE_CUSTOMER
        );
        CustomerEntity customerEntityToSave = CustomerMapper.toEntity(userToSave);
        CustomerEntity savedCustomerEntity = customerRepository.save(customerEntityToSave);
        return CustomerMapper.toDomain(savedCustomerEntity);
    }
}
