package com.peauty.persistence.Addy;

import com.peauty.persistence.config.BaseTimeEntity;
import com.peauty.persistence.puppy.PuppyEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addy")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddyEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puppy_id")
    private PuppyEntity puppy;

    @Lob
    @Column(name = "addy_image_url")
    private String addyImageUrl;
}
