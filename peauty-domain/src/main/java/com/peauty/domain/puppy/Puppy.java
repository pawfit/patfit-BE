package com.peauty.domain.puppy;

import com.peauty.domain.addy.AddyImage;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Puppy {

    private Long puppyId;               // 강아지 ID
    private Long customerId;            // 소유 고객 ID
    private String name;                // 강아지 이름
    private Breed breed;                // 강아지 품종
    private Long weight;                // 무게
    private Sex sex;                    // 성별
    private Integer age;                // 나이
    private LocalDate birthdate;        // 생일
    private String detail;              // 특이사항
    private List<Disease> disease;      // 질병
    private String diseaseDescription;  // 기타 질병사항
    private String profileImageUrl;     // 프로필 사진

    // 강아지 이미지 목록
    private List<AddyImage> Addy;       // Addy URL

    public Puppy assignCustomer(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void updatePuppyProfile(PuppyProfile profileToUpdate) {
        this.name = profileToUpdate.getName();
        this.breed = profileToUpdate.getBreed();
        this.weight = profileToUpdate.getWeight();
        this.sex = profileToUpdate.getSex();
        this.age = profileToUpdate.getAge();
        this.birthdate = profileToUpdate.getBirthdate();
        this.detail = profileToUpdate.getDetail();
        this.disease = profileToUpdate.getDisease();
        this.diseaseDescription = profileToUpdate.getDiseaseDescription();
        this.profileImageUrl = profileToUpdate.getProfileImageUrl();
    }

/*    public PuppyProfile getPuppyProfile() {
        return PuppyProfile.builder()
                .name(this.name)
                .breed(this.breed)
                .weight(this.weight)
                .sex(this.sex)
                .age(this.age)
                .birthdate(this.birthdate)
                .detail(this.detail)
                .disease(this.disease)
                .diseaseDescription(this.diseaseDescription)
                .profileImageUrl(this.profileImageUrl)
                .build();
    }*/

}
