package com.peauty.customer.business.customer;

import com.peauty.customer.business.auth.dto.SignUpCommand;
import com.peauty.domain.customer.Customer;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.Workspace;

import java.util.List;
import java.util.Optional;

public interface CustomerPort {
    void checkCustomerSocialIdDuplicated(String socialId);
    void checkCustomerNicknameDuplicated(String nickname);
    void checkCustomerPhoneNumDuplicated(String phoneNum);
    Optional<Customer> findBySocialId(String socialId);
    Customer save(Customer customer);
    Customer registerNewCustomer(SignUpCommand command);
    Customer getByCustomerIdWithoutPuppies(Long customerId);
    Customer getCustomerById(Long customerId);

    List<Workspace> findAllWorkspaceByAddress(String address);
    Designer findDesignerById(Long designerId);

}
