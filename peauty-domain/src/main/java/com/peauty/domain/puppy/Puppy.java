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
    private PuppyAgeInfo puppyAgeInfo;                // 나이
    private String detail;              // 특이사항
    private List<Disease> diseases;      // 질병
    private String diseaseDescription;  // 기타 질병사항
    private String profileImageUrl;     // 프로필 사진
    private PuppySize puppySize;        // 분류

    // 강아지 이미지 목록
    private List<AddyImage> addyImages;       // Addy URL

    public Puppy assignCustomer(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateBreed(Breed breed) {
        this.breed = breed;
    }

    public void updateWeight(Long weight) {
        this.weight = weight;
    }

    public void updateSex(Sex sex) {
        this.sex = sex;
    }



    public void updateDetail(String detail) {
        this.detail = detail;
    }

    public void updateDisease(List<Disease> disease) {
        this.diseases = disease;
    }

    public void updateDiseaseDescription(String diseaseDescription) {
        this.diseaseDescription = diseaseDescription;
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updatePuppySize(PuppySize puppySize) {
        this.puppySize = puppySize;
    }


    public void updateBirthdate(LocalDate birthdate) {
        puppyAgeInfo.updateBirthdate(birthdate);
    }

    public Profile getProfile() {
        return Profile.builder()
                .puppyId(puppyId)
                .customerId(customerId)
                .name(name)
                .breed(breed.getBreedName())
                .weight(weight)
                .sex(sex.getDescription())
                .age(puppyAgeInfo.getSimpeAge())
                .birthdate(puppyAgeInfo.getBirthdate())
                .profileImageUrl(profileImageUrl)
                .puppySize(puppySize.getDescription())
                .diseases(diseases.stream().map(Disease::getDescription).toList())
                .build();
    }

    public Profile getProfile(Boolean hasOngoingProcess) {
        return Profile.builder()
                .puppyId(puppyId)
                .customerId(customerId)
                .name(name)
                .breed(breed.getBreedName())
                .weight(weight)
                .sex(sex.getDescription())
                .age(puppyAgeInfo.getSimpeAge())
                .birthdate(puppyAgeInfo.getBirthdate())
                .profileImageUrl(profileImageUrl)
                .puppySize(puppySize.getDescription())
                .diseases(diseases.stream().map(Disease::getDescription).toList())
                .hasOngoingProcess(hasOngoingProcess)
                .build();
    }

    @Builder
    public record Profile(
            Long puppyId,
            Long customerId,
            String name,
            String breed,
            Long weight,
            String sex,
            Integer age,
            LocalDate birthdate,
            String profileImageUrl,
            String puppySize,
            List<String> diseases,
            Boolean hasOngoingProcess
    ) {
    }
}
