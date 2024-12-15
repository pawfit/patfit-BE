package com.peauty.designer.business.designer;

import com.peauty.designer.business.designer.dto.*;
import com.peauty.designer.business.internal.InternalPort;
import com.peauty.designer.business.workspace.WorkspacePort;
import com.peauty.domain.designer.Badge;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.License;
import com.peauty.domain.designer.Workspace;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DesignerServiceImpl implements DesignerService {

    private final DesignerPort designerPort;
    private final InternalPort internalPort;
    private final WorkspacePort workspacePort;

    @Override
    @Transactional
    public UploadProfileImageResult uploadProfileImage(Long designerId, MultipartFile file) {
        Designer designer = designerPort.getByDesignerId(designerId);
        String uploadedProfileImageUrl = internalPort.uploadImage(file);
        designer.updateProfileImageUrl(uploadedProfileImageUrl);
        return UploadProfileImageResult.from(designerPort.save(designer));
    }

    @Override
    public GetDesignerAccountResult getDesignerAccount(Long designerId) {
        Designer designer = designerPort.getByDesignerId(designerId);
        return GetDesignerAccountResult.from(designer);
    }

    @Override
    @Transactional
    public UpdateDesignerAccountResult updateDesignerAccount(Long designerId, UpdateDesignerAccountCommand command) {
        Designer designerToUpdate = designerPort.getByDesignerId(designerId);
        // TODO: 추후 updateDesignerAccount를 도메인에 만들어 한 줄로 코드 변경 예정
        designerToUpdate.updateName(command.name());
        designerToUpdate.updatePhoneNumber(command.phoneNumber());
        designerToUpdate.updateNickname(command.nickname());
        designerToUpdate.updateProfileImageUrl(command.profileImageUrl());
        designerToUpdate.updateEmail(command.email());
        Designer updatedDesigner = designerPort.save(designerToUpdate);
        return UpdateDesignerAccountResult.from(updatedDesigner);
    }

    @Override
    public void checkDesignerNicknameDuplicated(String nickname) {
        designerPort.checkDesignerNicknameDuplicated(nickname);
    }

    @Override
    @Transactional
    public CreateDesignerWorkspaceResult createDesignerWorkspace(Long userId, CreateDesignerWorkspaceCommand command) {
        Designer designerToCreate = designerPort.getByDesignerId(userId);
        Workspace workspaceToCreate = CreateDesignerWorkspaceCommand.toWorkspace(command);
        List<License> licenseToCreate = CreateDesignerWorkspaceCommand.toLicense(command);

        designerToCreate.updateYearOfExperience(command.yearOfExperience());
        designerToCreate.updateLicenses(licenseToCreate);

        Designer savedDesigner = designerPort.save(designerToCreate);
        Workspace savedWorkspace = workspacePort.registerNew(workspaceToCreate, savedDesigner.getDesignerId());
        Workspace getRating = workspacePort.findByDesignerId(userId);

        savedWorkspace.updateRating(getRating.getRating());
        savedDesigner.updateBadges(designerPort.getBadges(userId));

        return CreateDesignerWorkspaceResult.from(savedDesigner, savedWorkspace);
    }

    @Override
    public GetDesignerWorkspaceResult getDesignerWorkspace(Long userId) {
        Designer designer = designerPort.getAllDesignerDataByDesignerId(userId);
        Workspace workspace = workspacePort.findByDesignerId(userId);

        designer.updateBadges(designerPort.getBadges(userId));
        return GetDesignerWorkspaceResult.from(designer, workspace);
    }

    @Transactional
    @Override
    public UpdateDesignerWorkspaceResult updateDesignerWorkspace(Long userId, UpdateDesignerWorkspaceCommand command) {
        Workspace workspaceToUpdate = workspacePort.findByDesignerId(userId);
        workspaceToUpdate.updateWorkspace(UpdateDesignerWorkspaceCommand.toWorkspace(command));

        Workspace updatedWorkspace = workspacePort.updateDesginerWorkspace(userId, workspaceToUpdate);
        Designer getDesigner = designerPort.getAllDesignerDataByDesignerId(userId);

        return UpdateDesignerWorkspaceResult.from(getDesigner, updatedWorkspace);
    }

    // TODO: 조인쿼리로 변환하면 좋겠다.
    @Override
    public GetDesignerBadgesResult getDesignerBadges(Long userId) {
        // 디자이너가 획득한 뱃지 조회
        List<Badge> acquiredBadges = designerPort.getAcquiredBadges(userId);

        // 대표 뱃지 필터링
        List<Badge> representativeBadges = acquiredBadges.stream()
                .filter(Badge::getIsRepresentativeBadge)
                .toList();

        // 모든 뱃지 목록에서 획득하지 않은 뱃지를 계산
        List<Badge> allBadges = designerPort.getAllBadges();
        List<Badge> unacquiredBadges = allBadges.stream()
                .filter(badge -> acquiredBadges.stream()
                        .noneMatch(acquired -> acquired.getBadgeId().equals(badge.getBadgeId())))
                .toList();

        return GetDesignerBadgesResult.from(acquiredBadges, unacquiredBadges, representativeBadges);
    }

    @Transactional
    @Override
    public UpdateRepresentativeBadgeResult updateRepresentativeBadge(Long userId, Long badgeId, UpdateRepresentativeBadgeCommand command) {
        List<Badge> acquiredBadges = designerPort.getAcquiredBadges(userId);

        // 기존 대표 뱃지 확인
        List<Badge> representativeBadges = acquiredBadges.stream()
                .filter(Badge::getIsRepresentativeBadge)
                .collect(Collectors.toList());

        // 대표 뱃지가 3개 이상일 경우 접근 막음. 더 이상 추가할 수 없도록 하는 로직
        if (representativeBadges.size() >= 3 && command.isRepresentativeBadge()) {
            throw new PeautyException(PeautyResponseCode.ALREADY_FULL_BADGE);
        }

        // 해당 뱃지 조회
        Badge targetBadge = acquiredBadges.stream()
                .filter(b -> b.getBadgeId().equals(badgeId))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_BADGE));
        // 업데이트
        targetBadge.updateIsRepresentativeBadge(command.isRepresentativeBadge());
        // 저장
        designerPort.updateBadgeStatus(targetBadge, userId);
        // 결과 반환
        return UpdateRepresentativeBadgeResult.from(targetBadge.getBadgeId(), targetBadge.getBadgeName(), targetBadge.getIsRepresentativeBadge());
    }




}
