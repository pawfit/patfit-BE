package com.peauty.customer.business.customer.dto;

import com.peauty.domain.customer.Customer;

import java.util.List;

public record GetAroundWorkspacesResult(
        Long customerId,
        String customerAddress,
        List<GetAroundWorkspaceResult> workspaces
) {
    public static GetAroundWorkspacesResult from(Customer customer, List<GetAroundWorkspaceResult> workspaceResults) {
        return new GetAroundWorkspacesResult(
                customer.getCustomerId(),
                customer.getAddress(),
                workspaceResults
        );
    }
}