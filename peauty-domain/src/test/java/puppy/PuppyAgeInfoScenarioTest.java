package puppy;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.puppy.Breed;
import com.peauty.domain.puppy.Puppy;
import com.peauty.domain.puppy.PuppyAgeInfo;
import com.peauty.domain.puppy.Sex;
import com.peauty.domain.response.PeautyResponseCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

@DisplayName("반려견 등록 및 수정 API 테스트")
class PuppyAgeInfoScenarioTest {

    private Puppy puppy;

    @BeforeEach
    void setUp() {
        puppy = Puppy.builder()
                .puppyId(1L)
                .name("꼬미")
                .breed(Breed.DACHSHUND)
                .weight(10L)
                .sex(Sex.F)
                .puppyAgeInfo(new PuppyAgeInfo(LocalDate.of(2020, 6, 15)))
                .detail("사랑스러운 강아지")
                .build();
    }

    @Test
    @DisplayName("반려견 등록 시 생년월일로 나이 계산")
    void registerPuppyCalculatesAge() {
        LocalDate birthdate = LocalDate.of(2021, 6, 15);
        PuppyAgeInfo calculatedAge = new PuppyAgeInfo(birthdate);

        Puppy newPuppy = Puppy.builder()
                .name("초코")
                .breed(Breed.POODLE)
                .weight(20L)
                .sex(Sex.M)
                .puppyAgeInfo(calculatedAge)
                .detail("활발한 강아지")
                .build();

        assertThat(newPuppy.getPuppyAgeInfo().getBirthdate()).isEqualTo(birthdate);
        assertThat(newPuppy.getPuppyAgeInfo().getSimpeAge()).isEqualTo(3); // 현재 연도가 2024년 기준으로 계산
        assertThat(newPuppy.getPuppyAgeInfo().getFormattedAge()).isEqualTo("3년 6개월");
    }

    @Test
    @DisplayName("반려견 수정 시 생년월일 변경에 따른 나이 재계산")
    void updatePuppyRecalculatesAge() {
        // 기존 생년월일 확인
        assertThat(puppy.getPuppyAgeInfo().getBirthdate()).isEqualTo(LocalDate.of(2020, 6, 15));
        assertThat(puppy.getPuppyAgeInfo().getSimpeAge()).isEqualTo(4);
        assertThat(puppy.getPuppyAgeInfo().getFormattedAge()).isEqualTo("4년 6개월");

        // 생년월일 업데이트
        LocalDate newBirthdate = LocalDate.of(2022, 1, 1);
        puppy.getPuppyAgeInfo().updateBirthdate(new PuppyAgeInfo(newBirthdate).getBirthdate());

        // 나이 정보가 재계산되었는지 확인
        assertThat(puppy.getPuppyAgeInfo().getBirthdate()).isEqualTo(newBirthdate);
        assertThat(puppy.getPuppyAgeInfo().getSimpeAge()).isEqualTo(2);
        assertThat(puppy.getPuppyAgeInfo().getFormattedAge()).isEqualTo("2년 11개월");
    }

    @Test
    @DisplayName("유효하지 않은 생년월일로 반려견 등록 시 예외 발생")
    void invalidBirthdateThrowsException() {
        LocalDate futureBirthdate = LocalDate.of(2025, 1, 1);

        assertThatThrownBy(() -> new PuppyAgeInfo(futureBirthdate))
                .isInstanceOf(PeautyException.class)
                .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.INVALID_BIRTHDATE);
    }

    @Test
    @DisplayName("반려견 등록 후 생년월일과 나이 정확성 확인")
    void validatePuppyRegistrationDetails() {
        Puppy registeredPuppy = Puppy.builder()
                .name("뭉치")
                .breed(Breed.POODLE)
                .weight(5L)
                .sex(Sex.M)
                .puppyAgeInfo(new PuppyAgeInfo(LocalDate.of(2023, 3, 10)))
                .detail("귀여운 강아지")
                .build();

        assertThat(registeredPuppy.getName()).isEqualTo("뭉치");
        assertThat(registeredPuppy.getBreed()).isEqualTo(Breed.POODLE);
        assertThat(registeredPuppy.getPuppyAgeInfo().getBirthdate()).isEqualTo(LocalDate.of(2023, 3, 10));
        assertThat(registeredPuppy.getPuppyAgeInfo().getFormattedAge()).isEqualTo("1년 9개월");
    }
}
