package com.peauty.persistence.puppy;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.puppy.Breed;
import com.peauty.domain.puppy.Disease;
import com.peauty.domain.puppy.PuppySize;
import com.peauty.domain.puppy.Sex;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.persistence.config.BaseTimeEntity;
import com.peauty.persistence.customer.CustomerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "puppy")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PuppyEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "puppy_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @Column(name = "name", length = 15, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "breed", nullable = false)
    private Breed breed;

    @Column(name = "weight", nullable = false)
    private Long weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "detail")
    private String detail;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
    name = "puppy_disease", // 컬렉션 테이블 이름
    joinColumns = @JoinColumn(name = "puppy_id") // 외래 키
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "disease")
    private List<Disease> disease;


    @Column(name = "disease_description")
    private String diseaseDescription;

    @Lob
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "size", nullable = false)
    private PuppySize puppySize; // 소형, 중형, 대형


    // 업데이트 메서드
    public void update(String name, Breed breed, Long weight, Sex sex, int age, LocalDate birthdate,
                              String detail, List<Disease> disease, String diseaseDescription, String profileImageUrl, PuppySize puppySize) {
        this.name = name;
        this.breed = breed;
        this.weight = weight;
        this.sex = sex;
        this.age = age;
        this.birthdate = birthdate;
        this.detail = detail;
        this.disease = disease;
        this.diseaseDescription = diseaseDescription;
        this.profileImageUrl = profileImageUrl;
        this.puppySize = puppySize;
    }

    public void assignCustomer(CustomerEntity customer) {
        if (customer == null) {
            throw new PeautyException(PeautyResponseCode.NOT_EXIST_USER);
        }
        this.customer = customer;
    }

}
