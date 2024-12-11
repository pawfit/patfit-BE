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
    private PuppyAge age;                // 나이
//    private LocalDate birthdate;        // 생일
    private String detail;              // 특이사항
    private List<Disease> disease;      // 질병
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
        this.disease = disease;
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

    public void updateAge(LocalDate birthdate) {
        this.age = new PuppyAge(birthdate);
    }

    public int getAgeInYears() {
        return age.getYears();
    }

    public String getFormattedAge() {
        return age.getFormattedAge();
    }

    public LocalDate getBirthdate() {
        return age.getBirthdate();
    }

    public void updateBirthdate(LocalDate birthdate) {
        this.age = new PuppyAge(birthdate);
    }

    public Profile getProfile() {
        return new Profile(
                puppyId,
                customerId,
                name,
                breed.getBreedName(),
                weight,
                sex.getDescription(),
                getAgeInYears(),
                getBirthdate(),
                profileImageUrl,
                puppySize.getDescription()
        );
    }

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
            String puppySize
    ) {
    }

/*    public void updatePuppyProfile(PuppyProfile profileToUpdate) {
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
        this.puppySize = profileToUpdate.getPuppySize();
    }*/

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
