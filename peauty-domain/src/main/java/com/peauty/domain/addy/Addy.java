package com.peauty.domain.addy;

import lombok.*;

@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Addy {

    private Long addyId;            // Addy ID
    private String addyImageUrl;    // 이미지 URL

}
