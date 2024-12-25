package com.peauty.customer.business.workspace;

import com.peauty.customer.business.customer.CustomerPort;
import com.peauty.customer.business.workspace.dto.GetAroundWorkspaceResult;
import com.peauty.customer.business.workspace.dto.GetAroundWorkspacesResult;
import com.peauty.customer.business.designer.DesignerPort;
import com.peauty.customer.business.workspace.dto.GetDesignerWorkspaceResult;
import com.peauty.domain.customer.Customer;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.Workspace;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkspaceImpl implements WorkspaceService {
    private final DesignerPort designerPort;
    private final WorkspacePort workspacePort;
    private final CustomerPort customerPort;

    @Override
    public GetDesignerWorkspaceResult getWorkspaceDetails(Long designerId) {
        Designer designer = designerPort.getAllDesignerDataByDesignerId(designerId);
        Workspace workspace = workspacePort.findByDesignerId(designerId);

        designer.updateBadges(designerPort.getRepresentativeBadges(designerId));
        return GetDesignerWorkspaceResult.from(designer, workspace);
    }

    @Override
    public GetAroundWorkspacesResult getAroundWorkspaces(Long customerId) {
        // 고객 정보 조회
        Customer customer = customerPort.getCustomerById(customerId);
        // OO시 OO구 까지 추출
        String customerBaseAddress = extractBaseAddress(customer.getAddress());
        // 고객의 상위주소와 맞는 미용실 전체 조회
        List<Workspace> workspaces = workspacePort.findAllWorkspaceByAddress(customerBaseAddress);
        // 각 미용실에 해당하는 디자이너 정보를 매핑
        List<GetAroundWorkspaceResult> workspaceResults = workspaces.stream()
                .map(workspace -> {
                    // 미용실을 소유한 디자이너 조회
                    Designer designer = workspacePort.findDesignerById(workspace.getDesignerId());
                    // 대표 뱃지 정보 가져오기 (이름과 색상, 타입 포함)
                    List<GetAroundWorkspaceResult.Badge> representativeBadges = designerPort.getRepresentativeBadges(designer.getDesignerId())
                            .stream()
                            .map(badge -> new GetAroundWorkspaceResult.Badge(
                                    badge.getBadgeId(),
                                    badge.getBadgeName(),
                                    badge.getBadgeContent(),
                                    badge.getBadgeImageUrl(),
                                    badge.getBadgeColor(),
                                    badge.getBadgeType()
                            ))
                            .toList();
                    return GetAroundWorkspaceResult.from(workspace, designer, representativeBadges);
                })
                .toList();

        return GetAroundWorkspacesResult.from(customer, workspaceResults);
    }

    private String extractBaseAddress(String address) {
        // 어절(띄어쓰기)로 파싱.
        // 서울 강남구 대치동 -> 서울 강남구
        String[] addressParts = address.split(" ");
        if (addressParts.length < 2) {
            // "" or "서울시"
            throw new PeautyException(PeautyResponseCode.INVALID_ADDRESS_FORMAT);
        }
        // 상위 주소만 딱 반환해버리기
        return addressParts[0] + " " + addressParts[1];
    }
}
