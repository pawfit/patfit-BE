package com.peauty.domain.designer;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PaymentOption {
    CARD("카드 결제"),
    CASH("현금 결제"),
    ACCOUNT("계좌 이체");

    private final String optionName;

    PaymentOption(String optionName) {
        this.optionName = optionName;
    }

    public static PaymentOption from(String paymentType) {
        return Arrays.stream(PaymentOption.values())
                .filter(option -> option.name().equalsIgnoreCase(paymentType))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.ALREADY_CANCELED_BIDDING_PROCESS));
    }
}
