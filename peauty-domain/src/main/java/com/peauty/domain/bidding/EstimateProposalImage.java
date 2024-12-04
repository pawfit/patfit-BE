package com.peauty.domain.bidding;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder
public class EstimateProposalImage {

    private ID id;
    @Getter private EstimateProposal.ID estimateProposalId;
    @Getter private String imageUrl;

    public Optional<EstimateProposalImage.ID> getId() {
        return Optional.ofNullable(id);
    }

    public record ID(Long value) {
        public boolean isSameId(Long id) {
            return value.equals(id);
        }
    }
}
