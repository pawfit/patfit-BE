package com.peauty.domain.bidding;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BiddingProcessTimeInfo {

    private LocalDateTime createdAt;
    private LocalDateTime statusModifiedAt;

    protected void onStatusChange() {
        this.statusModifiedAt = LocalDateTime.now();
    }

    public static BiddingProcessTimeInfo createNewTimeInfo() {
        return new BiddingProcessTimeInfo(
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
