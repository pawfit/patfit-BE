package com.peauty.domain.bidding;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Optional;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Estimate {

    private final ID id;
    @Getter
    private final BiddingThread.ID threadId;
    @Getter
    private String content;
    @Getter
    private LocalDate date;
    @Getter
    private Integer cost;
    @Getter
    private String proposalImageUrl;

    public Optional<Estimate.ID> getId() {
        return Optional.ofNullable(this.id);
    }

    public record ID(Long value) {
        public boolean isSameId(Long id) {
            return value.equals(id);
        }
    }
}
