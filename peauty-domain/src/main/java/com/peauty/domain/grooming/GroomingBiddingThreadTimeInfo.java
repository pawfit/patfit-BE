package com.peauty.domain.grooming;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GroomingBiddingThreadTimeInfo {

    private LocalDateTime createdAt;
    private LocalDateTime stepModifiedAt;
    private LocalDateTime statusModifiedAt;

    public void onStepChange() {
        this.stepModifiedAt = LocalDateTime.now();
    }

    public void onStatusChange() {
        this.statusModifiedAt = LocalDateTime.now();
    }

    public static GroomingBiddingThreadTimeInfo createNewTimeInfo() {
        return new GroomingBiddingThreadTimeInfo(
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
