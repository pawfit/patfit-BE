package com.peauty.domain.puppy;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PuppyProfile {
    private String name;
    private Breed breed;
    private Long weight;
    private Sex sex;
    private Integer age;
    private LocalDate birthdate;
    private String detail;
    private List<Disease> disease;
    private String diseaseDescription;
    private String profileImageUrl;
    private PuppySize puppySize;
}
