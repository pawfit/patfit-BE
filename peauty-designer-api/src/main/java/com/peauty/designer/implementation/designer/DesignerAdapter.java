package com.peauty.designer.implementation.designer;

import com.peauty.designer.business.auth.dto.SignUpCommand;
import com.peauty.designer.business.designer.DesignerPort;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.DesignerInfo;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.user.Role;
import com.peauty.domain.user.Status;
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
        if (designerRepository.existsByPhoneNumber(phoneNum)) {
            throw new PeautyException(PeautyResponseCode.ALREADY_EXIST_PHONE_NUM);
        }
    }

    @Override
    public Optional<Designer> findBySocialId(String socialId) {
        return Optional.ofNullable(designerRepository.findBySocialId(socialId))
                .orElse(Optional.empty())
                .map(DesignerMapper::toDomain);
    }

    @Override
    public Designer save(Designer designer) {
        DesignerEntity designerEntityToSave = DesignerMapper.toEntity(designer);
        DesignerEntity savedDesignerEntity = designerRepository.save(designerEntityToSave);
        return DesignerMapper.toDomain(savedDesignerEntity);
    }

    @Override
    public Designer registerNewDesigner(SignUpCommand command) {
        Designer designerToSave = Designer.builder()
                .designerId(0L)
                .socialId(command.socialId())
                .socialPlatform(command.socialPlatform())
                .name(command.name())
                .phoneNumber(command.phoneNumber())
                .email(command.email())
                .status(Status.ACTIVE)
                .role(Role.ROLE_DESIGNER)
                .nickname(command.nickname())
                .profileImageUrl(command.profileImageUrl())
                .designerInfo(DesignerInfo.getFirstDesignerInfo())
                .build();
        return save(designerToSave);
    }

    @Override
    public Designer getByDesignerId(Long designerId) {
        return designerRepository.findById(designerId)
                .map(DesignerMapper::toDomain)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_USER));
    }
}
