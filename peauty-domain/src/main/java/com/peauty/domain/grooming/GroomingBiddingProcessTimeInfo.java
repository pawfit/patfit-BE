package com.peauty.domain.grooming;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GroomingBiddingProcessTimeInfo {

    private LocalDateTime createdAt;
    private LocalDateTime statusModifiedAt;

    public void onStatusChange() {
        this.statusModifiedAt = LocalDateTime.now();
    }

    public static GroomingBiddingProcessTimeInfo createNewTimeInfo() {
        return new GroomingBiddingProcessTimeInfo(
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
