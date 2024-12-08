package com.peauty.designer.implementation.puppy;

import com.peauty.designer.business.puppy.PuppyPort;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.puppy.Puppy;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.persistence.customer.CustomerEntity;
import com.peauty.persistence.customer.CustomerRepository;
import com.peauty.persistence.puppy.PuppyEntity;
import com.peauty.persistence.puppy.PuppyMapper;
import com.peauty.persistence.puppy.PuppyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PuppyAdapter implements PuppyPort {

    private final PuppyRepository puppyRepository;

    @Override
    public Puppy getPuppyByCustomerIdAndPuppyId(Long userId, Long puppyId) {
        PuppyEntity puppyEntity = puppyRepository.findByIdAndCustomerId(puppyId, userId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_PUPPY));
        return PuppyMapper.toDomain(puppyEntity);
    }

    @Override
    public Puppy getPuppyByPuppyId(Long puppyId){
        return puppyRepository.findById(puppyId)
                .map(PuppyMapper::toDomain)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_PUPPY));
    }

    @Override
    public List<Puppy> findAllByCustomerId(Long customerId){
        return puppyRepository.findAllByCustomerId(customerId)
                .stream()
                .map(PuppyMapper::toDomain)
                .toList();
    }
}