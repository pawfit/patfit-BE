package com.peauty.persistence.designer;

import com.peauty.persistence.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shop")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ShopEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designer_id")
    private DesignerEntity designer;

    @Column(name = "shop_name", length = 255, nullable = false)
    private String shopName;

    @Column(name = "direction_guide", length = 255, nullable = false)
    private String directionGuide;

    @Column(name = "open_hours", length = 255, nullable = false)
    private String openHours;

    @Column(name = "payment_options", length = 255, nullable = false)
    private String paymentOptions;

}
