package com.peauty.customer.business.customer;

import com.peauty.customer.business.auth.dto.SignUpCommand;
import com.peauty.domain.customer.Customer;

import java.util.Optional;

public interface CustomerPort {
    void checkCustomerSocialIdDuplicated(String socialId);
    void checkCustomerNicknameDuplicated(String nickname);
    void checkCustomerPhoneNumberDuplicated(String phoneNum);
    Optional<Customer> findBySocialId(String socialId);
    Customer save(Customer customer);
    Customer registerNewCustomer(SignUpCommand command);
    Customer getByCustomerIdWithoutPuppies(Long customerId);
    Customer getCustomerById(Long customerId);



}
