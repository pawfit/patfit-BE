package com.peauty.domain.addy;

import lombok.*;

@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    /**
     * 24.11.21 작성자 : 신재혁
     * Column
     */
    private Long imageId; // 이미지 ID
//    private Long puppyId; // 강아지 ID
    private String addyUrl; // 이미지 URL

}
