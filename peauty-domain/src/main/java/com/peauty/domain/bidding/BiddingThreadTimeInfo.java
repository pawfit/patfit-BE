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

    public void onStepChange() {
        this.stepModifiedAt = LocalDateTime.now();
    }

    public void onStatusChange() {
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
