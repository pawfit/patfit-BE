package com.peauty.domain.bidding;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BiddingThreadTimeInfo {

    private LocalDateTime createdAt;
    private LocalDateTime stepModifiedAt;
    private LocalDateTime statusModifiedAt;

    protected void onStepChange() {
        this.stepModifiedAt = LocalDateTime.now();
    }

    protected void onStatusChange() {
        this.statusModifiedAt = LocalDateTime.now();
    }

    public static BiddingThreadTimeInfo createNewTimeInfo() {
        return new BiddingThreadTimeInfo(
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
