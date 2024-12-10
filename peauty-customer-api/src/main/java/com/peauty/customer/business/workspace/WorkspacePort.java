package com.peauty.customer.business.workspace;

import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.Rating;
import com.peauty.domain.designer.Workspace;
import com.peauty.domain.review.ReviewRating;

import java.util.List;

public interface WorkspacePort {

    List<Workspace> findAllWorkspaceByAddress(String address);
    Designer findDesignerById(Long designerId);
    Rating getRatingByWorkspaceId(Long workspaceId); // 추가된 메서드
    Workspace getByDesignerId(Long designerId);

//    void saveWorkspace(Workspace workspace);
    Workspace registerReviewStats(Long workspaceId, ReviewRating newRating);
    Workspace updateReviewStats(Long designerId, ReviewRating oldRating, ReviewRating newRating);
    Workspace deleteReviewStats(Long designerId, ReviewRating deletedRating);
}
