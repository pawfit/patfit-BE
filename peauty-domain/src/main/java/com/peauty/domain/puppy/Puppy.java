package com.peauty.domain.puppy;

import com.peauty.domain.addy.Image;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Puppy {
    /**
     * 24.11.21 작성자 : 신재혁
     * Column
    */
    private Long puppyId;                // 강아지 ID
//    private Long customerId;          // 소유 고객 ID
    private String name;                // 강아지 이름
    private String breed;               // 강아지 품종
    private Long weight;                // 무게
    private Sex sex;                    // 성별
    private Long age;                   // 나이
    private LocalDate birthdate;        // 생일
    private String detail;              // 특이사항
    private String disease;             // 질병
    private String profileUrl;          // 프로필 사진

    // 강아지 이미지 목록
    private List<Image> images;     // 이미지 URL

}
