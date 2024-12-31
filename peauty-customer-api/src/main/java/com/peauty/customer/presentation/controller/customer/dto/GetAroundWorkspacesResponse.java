package com.peauty.customer.presentation.controller.customer.dto;

import com.peauty.customer.business.workspace.dto.GetAroundWorkspacesResult;

import java.util.List;

public record GetAroundWorkspacesResponse(
        Long customerId,
        String customerAddress,
        List<GetAroundWorkspaceResponse> workspaces
) {
    public static GetAroundWorkspacesResponse from(GetAroundWorkspacesResult result) {
        List<GetAroundWorkspaceResponse> workspaceResponses = result.workspaces().stream()
                .map(GetAroundWorkspaceResponse::from)
                .toList();

        return new GetAroundWorkspacesResponse(
                result.customerId(),
                result.customerAddress(),
                workspaceResponses
        );
    }
}