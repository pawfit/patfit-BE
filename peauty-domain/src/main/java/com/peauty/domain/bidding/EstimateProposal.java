package com.peauty.domain.bidding;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Builder
public class EstimateProposal {

    private ID id;
    @Getter private BiddingProcess.ID processId;
    @Getter private GroomingType type;
    @Getter private String detail;
    @Getter private List<EstimateProposalImage> images;
    @Getter private Long desiredCost;
    @Getter private LocalDateTime desiredDateTime;

    // can be null depends on grooming type
    @Getter private TotalGroomingBodyType totalGroomingBodyType;
    @Getter private TotalGroomingFaceType totalGroomingFaceType;

    public Optional<EstimateProposal.ID> getId() {
        return Optional.ofNullable(id);
    }

    public record ID(Long value) {
        public boolean isSameId(Long id) {
            return value.equals(id);
        }
    }
}