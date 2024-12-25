package com.peauty.designer.business.bidding.dto;

import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.designer.Designer;
import lombok.Builder;

import java.util.List;

public record GetDesignerScheduleResult(
        Designer.DesignerProfile designerProfile,
        List<BiddingProcess.ProcessProfile> processProfiles,
        GetDesignerScheduleResultGroomingSummary groomingSummary
) {

    @Builder
    public record GetDesignerScheduleResultGroomingSummary(
            Integer endGroomingCount,
            Integer todayGroomingCount,
            Integer nextGroomingCount
    ) {
    }

    public static GetDesignerScheduleResult from(
            Designer.DesignerProfile designerProfile,
            List<BiddingProcess.ProcessProfile> processProfiles,
            Integer endGroomingCount,
            Integer todayGroomingCount,
            Integer nextGroomingCount
    ) {
        return new GetDesignerScheduleResult(
                designerProfile,
                processProfiles,
                new GetDesignerScheduleResultGroomingSummary(
                        endGroomingCount,
                        todayGroomingCount,
                        nextGroomingCount
                )
        );
    }
}
