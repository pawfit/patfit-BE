package com.peauty.designer.implementation.designer;

import com.peauty.designer.business.auth.dto.SignUpCommand;
import com.peauty.designer.business.designer.DesignerPort;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.user.Role;
import com.peauty.domain.user.Status;
import com.peauty.domain.user.User;
import com.peauty.persistence.designer.DesignerEntity;
import com.peauty.persistence.designer.DesignerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DesignerAdapter implements DesignerPort {

    private final DesignerRepository designerRepository;

    @Override
    public void checkCustomerNicknameDuplicated(String nickname) {
        if (designerRepository.existsByNickname(nickname)) {
            throw new PeautyException(PeautyResponseCode.ALREADY_EXIST_USER);
        }
    }

    @Override
    public void checkCustomerPhoneNumDuplicated(String phoneNum) {
        if (designerRepository.existsByPhoneNum(phoneNum)) {
            throw new PeautyException(PeautyResponseCode.ALREADY_EXIST_PHONE_NUM);
        }
    }

    @Override
    public Optional<User> findBySocialId(String socialId) {
        return Optional.ofNullable(designerRepository.findBySocialId(socialId))
                .orElse(Optional.empty())
                .map(DesignerMapper::toDomain);
    }

    @Override
    public User save(User designer) {
        DesignerEntity designerEntityToSave = DesignerMapper.toEntity(designer);
        DesignerEntity savedDesignerEntity = designerRepository.save(designerEntityToSave);
        return DesignerMapper.toDomain(savedDesignerEntity);
    }

    @Override
    public User registerNewDesigner(SignUpCommand command) {
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
                Role.ROLE_DESIGNER
        );
        return save(userToSave);
    }

    @Override
    public User getByUserId(Long userId) {
        return designerRepository.findById(userId)
                .map(DesignerMapper::toDomain)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_USER));
    }
}
