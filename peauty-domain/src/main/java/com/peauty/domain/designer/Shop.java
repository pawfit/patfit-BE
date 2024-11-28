package com.peauty.domain.designer;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

    private Long shopId;
    private String shopName;
    private String directionGuide;
    private String openHours;
    private String paymentOptions;

    public void updateShopName(String shopName) {
        this.shopName = shopName;
    }

    public void updateDirectionGuide(String directionGuide) {
        this.directionGuide = directionGuide;
    }

    public void updateOpenHours(String openHours) {
        this.openHours = openHours;
    }

    public void updatePaymentOptions(String paymentOptions) {
        this.paymentOptions = paymentOptions;
    }
}
