package com.peauty.persistence.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByNickname(String nickname);
    boolean existsByPhoneNum(String phoneNum);
    Optional<CustomerEntity> findByOidcProviderId(String oidcProviderId);
}