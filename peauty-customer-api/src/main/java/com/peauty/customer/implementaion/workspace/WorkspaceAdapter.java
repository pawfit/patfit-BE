package com.peauty.customer.implementaion.workspace;

import com.peauty.customer.business.workspace.WorkspacePort;
import com.peauty.customer.implementaion.customer.CustomerMapper;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.Workspace;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.persistence.designer.DesignerRepository;
import com.peauty.persistence.designer.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkspaceAdapter implements WorkspacePort {

    private final DesignerRepository designerRepository;
    private final WorkspaceRepository workspaceRepository;

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
