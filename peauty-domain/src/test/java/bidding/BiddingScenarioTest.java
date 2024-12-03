package bidding;

import com.peauty.domain.bidding.*;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("입찰 시나리오 테스트")
class BiddingScenarioTest {
    private static final Long PROCESS_ID = 1L;
    private static final Long PUPPY_ID = 1L;
    private static final Long DESIGNER_ID_1 = 1L;
    private static final Long DESIGNER_ID_2 = 2L;

    private BiddingProcess process;
    private DesignerId designerId1;
    private DesignerId designerId2;

    @BeforeEach
    void setUp() {
        designerId1 = new DesignerId(DESIGNER_ID_1);
        designerId2 = new DesignerId(DESIGNER_ID_2);
        process = BiddingProcess.loadProcess(
                new BiddingProcess.ID(PROCESS_ID),
                new PuppyId(PUPPY_ID),
                BiddingProcessStatus.RESERVED_YET,
                BiddingProcessTimeInfo.createNewTimeInfo(),
                new ArrayList<>()
        );
    }

    @Nested
    @DisplayName("프로세스 생성 테스트")
    class CreationTest {
        @Test
        @DisplayName("새로운 프로세스가 정상적으로 생성된다")
        void createNewProcess() {
            process = BiddingProcess.createNewProcess(new PuppyId(PUPPY_ID));
            assertThat(process.getId()).isEmpty();
            assertThat(process.getPuppyId().value()).isEqualTo(PUPPY_ID);
            assertThat(process.getStatus()).isEqualTo(BiddingProcessStatus.RESERVED_YET);
            assertThat(process.getThreads()).isEmpty();
        }

        @Test
        @DisplayName("기존 프로세스가 정상적으로 로드된다")
        void loadProcess() {
            BiddingProcess loadedProcess = BiddingProcess.loadProcess(
                    new BiddingProcess.ID(PROCESS_ID),
                    new PuppyId(PUPPY_ID),
                    BiddingProcessStatus.RESERVED_YET,
                    BiddingProcessTimeInfo.createNewTimeInfo(),
                    new ArrayList<>()
            );

            assertThat(loadedProcess.getId()).hasValue(new BiddingProcess.ID(PROCESS_ID));
            assertThat(loadedProcess.getPuppyId().value()).isEqualTo(PUPPY_ID);
            assertThat(loadedProcess.getStatus()).isEqualTo(BiddingProcessStatus.RESERVED_YET);
        }
    }

    @Nested
    @DisplayName("스레드 관리 테스트")
    class ThreadManagementTest {

        @BeforeEach
        void setUp() {
            process = BiddingProcess.loadProcess(
                    new BiddingProcess.ID(PROCESS_ID),
                    new PuppyId(PUPPY_ID),
                    BiddingProcessStatus.RESERVED_YET,
                    BiddingProcessTimeInfo.createNewTimeInfo(),
                    new ArrayList<>()
            );
        }

        @Test
        @DisplayName("새로운 스레드를 추가할 수 있다")
        void addNewThread() {
            process.addNewThread(designerId1);

            assertThat(process.getThreads()).hasSize(1);
            BiddingThread thread = process.getThread(designerId1);
            assertThat(thread.getDesignerId()).isEqualTo(designerId1);
            assertThat(thread.getStep()).isEqualTo(BiddingThreadStep.ESTIMATE_REQUEST);
        }

        @Test
        @DisplayName("이미 존재하는 디자이너의 스레드는 추가할 수 없다")
        void cannotAddDuplicateThread() {
            process.addNewThread(designerId1);

            assertThatThrownBy(() -> process.addNewThread(designerId1))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.THREAD_ALREADY_IN_PROCESS);
        }

        @Test
        @DisplayName("취소된 프로세스에는 스레드를 추가할 수 없다")
        void cannotAddThreadToCanceledProcess() {
            process.cancel();

            assertThatThrownBy(() -> process.addNewThread(designerId1))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.ALREADY_CANCELED_BIDDING_PROCESS);
        }

        @Test
        @DisplayName("디자이너의 ID로 스레드를 조회할 수 있다")
        void getThreadById() {
            process.addNewThread(designerId1);
            BiddingThread foundThread = process.getThread(designerId1);

            assertThat(foundThread.getDesignerId().value()).isEqualTo(designerId1.value());
        }

        @Test
        @DisplayName("존재하지 않는 스레드 조회 시 예외가 발생한다")
        void throwExceptionWhenThreadNotFound() {
            assertThatThrownBy(() -> process.getThread(designerId1))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.NOT_FOUND_BIDDING_THREAD_IN_PROCESS);
        }
    }

    @Nested
    @DisplayName("스레드 상태 변경 테스트")
    class ThreadStateChangeTest {
        @BeforeEach
        void addThread() {
            process.addNewThread(designerId1);
        }

        @Test
        @DisplayName("스레드가 견적 응답 상태로 변경된다")
        void responseEstimate() {
            process.responseEstimateThread(designerId1);

            BiddingThread thread = process.getThread(designerId1);
            assertThat(thread.getStep()).isEqualTo(BiddingThreadStep.ESTIMATE_RESPONSE);
        }

        @Test
        @DisplayName("스레드가 예약 상태로 변경되면 다른 스레드들은 대기 상태가 된다")
        void reserve() {
            process.addNewThread(designerId2);
            process.responseEstimateThread(designerId1);
            process.reserveThread(designerId1);

            BiddingThread reservedThread = process.getThread(designerId1);
            BiddingThread waitingThread = process.getThread(designerId2);

            assertThat(reservedThread.getStep()).isEqualTo(BiddingThreadStep.RESERVED);
            assertThat(waitingThread.getStatus()).isEqualTo(BiddingThreadStatus.WAITING);
            assertThat(process.getStatus()).isEqualTo(BiddingProcessStatus.RESERVED);
        }

        @Test
        @DisplayName("예약된 스레드가 취소되면 다른 스레드들이 정상 상태로 돌아온다")
        void cancelReservedThread() {
            process.addNewThread(designerId2);
            process.responseEstimateThread(designerId1);
            process.reserveThread(designerId1);
            process.cancelThread(designerId1);

            BiddingThread canceledThread = process.getThread(designerId1);
            BiddingThread normalThread = process.getThread(designerId2);

            assertThat(canceledThread.getStatus()).isEqualTo(BiddingThreadStatus.CANCELED);
            assertThat(normalThread.getStatus()).isEqualTo(BiddingThreadStatus.NORMAL);
            assertThat(process.getStatus()).isEqualTo(BiddingProcessStatus.RESERVED_YET);
        }

        @Test
        @DisplayName("스레드가 완료되면 프로세스도 완료 상태가 된다")
        void complete() {
            process.responseEstimateThread(designerId1);
            process.reserveThread(designerId1);
            process.completeThread(designerId1);

            BiddingThread thread = process.getThread(designerId1);
            assertThat(thread.getStep()).isEqualTo(BiddingThreadStep.COMPLETED);
            assertThat(process.getStatus()).isEqualTo(BiddingProcessStatus.COMPLETED);
        }

        @Test
        @DisplayName("취소된 스레드는 견적 응답으로 진행할 수 없다")
        void cannotProgressCanceledThread() {
            process.cancelThread(designerId1);

            assertThatThrownBy(() -> process.responseEstimateThread(designerId1))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.CANNOT_PROGRESS_CANCELED_THREAD_STEP);
        }

        @Test
        @DisplayName("대기 상태의 스레드는 견적 응답으로 진행할 수 없다")
        void cannotProgressWaitingThread() {
            process.addNewThread(designerId2);
            process.responseEstimateThread(designerId1);
            process.reserveThread(designerId1);  // 이때 designerId2 스레드는 대기상태가 됨

            assertThatThrownBy(() -> process.responseEstimateThread(designerId2))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.CANNOT_PROGRESS_WAITING_THREAD_STEP);
        }

        @Test
        @DisplayName("견적 응답 없이 예약 상태로 진행할 수 없다")
        void cannotReserveWithoutEstimateResponse() {
            assertThatThrownBy(() -> process.reserveThread(designerId1))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.INVALID_STEP_PROGRESSING);
        }

        @Test
        @DisplayName("예약 없이 완료 상태로 진행할 수 없다")
        void cannotCompleteWithoutReservation() {
            process.responseEstimateThread(designerId1);

            assertThatThrownBy(() -> process.completeThread(designerId1))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.INVALID_STEP_PROGRESSING);
        }



        @Test
        @DisplayName("이미 취소된 스레드는 다시 취소할 수 없다")
        void cannotCancelAlreadyCanceledThread() {
            process.cancelThread(designerId1);

            assertThatThrownBy(() -> process.cancelThread(designerId1))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.ALREADY_CANCELED_BIDDING_THREAD);
        }


        @Test
        @DisplayName("예약된 스레드가 취소되면 대기 중이던 스레드가 다시 예약 가능해진다")
        void canReserveAfterReservedThreadCanceled() {
            process.addNewThread(designerId2);
            process.responseEstimateThread(designerId2);
            process.responseEstimateThread(designerId1);
            process.reserveThread(designerId1);

            assertThatThrownBy(() -> process.reserveThread(designerId2))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.CANNOT_PROGRESS_WAITING_THREAD_STEP);

            process.cancelThread(designerId1);

            // designerId2 스레드가 다시 예약 가능해짐
            assertDoesNotThrow(() -> {
                process.reserveThread(designerId2);
                BiddingThread reservedThread = process.getThread(designerId2);
                assertThat(reservedThread.getStep()).isEqualTo(BiddingThreadStep.RESERVED);
            });
        }
    }

    @Nested
    @DisplayName("프로세스 상태 제약 테스트")
    class ProcessStatusConstraintTest {
        @BeforeEach
        void addThread() {
            process.addNewThread(designerId1);
        }

        @Test
        @DisplayName("취소된 프로세스에서는 어떠한 스레드 동작도 불가능하다")
        void cannotDoAnyOperationInCanceledProcess() {
            // 프로세스를 취소 상태로 만듦
            process.cancel();
            assertThat(process.getStatus()).isEqualTo(BiddingProcessStatus.CANCELED);

            // 새로운 스레드 추가 시도
            assertThatThrownBy(() -> process.addNewThread(designerId2))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.ALREADY_CANCELED_BIDDING_PROCESS);

            // 기존 스레드에 대한 모든 동작 시도
            assertThatThrownBy(() -> process.responseEstimateThread(designerId1))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.ALREADY_CANCELED_BIDDING_PROCESS);

            assertThatThrownBy(() -> process.reserveThread(designerId1))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.ALREADY_CANCELED_BIDDING_PROCESS);

            assertThatThrownBy(() -> process.completeThread(designerId1))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.ALREADY_CANCELED_BIDDING_PROCESS);

            assertThatThrownBy(() -> process.cancelThread(designerId1))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.ALREADY_CANCELED_BIDDING_PROCESS);
        }

        @Test
        @DisplayName("완료된 프로세스에서는 어떠한 스레드 동작도 불가능하다")
        void cannotDoAnyOperationInCompletedProcess() {
            // 첫 번째 스레드를 완료 상태로 만듦
            process.responseEstimateThread(designerId1);
            process.reserveThread(designerId1);
            process.completeThread(designerId1);

            assertThat(process.getStatus()).isEqualTo(BiddingProcessStatus.COMPLETED);

            // 새로운 스레드 추가 시도
            assertThatThrownBy(() -> process.addNewThread(designerId2))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.ALREADY_COMPLETED_BIDDING_PROCESS);

            // 기존 스레드에 대한 모든 동작 시도
            assertThatThrownBy(() -> process.responseEstimateThread(designerId1))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.ALREADY_COMPLETED_BIDDING_PROCESS);

            assertThatThrownBy(() -> process.reserveThread(designerId1))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.ALREADY_COMPLETED_BIDDING_PROCESS);

            assertThatThrownBy(() -> process.cancelThread(designerId1))
                    .isInstanceOf(PeautyException.class)
                    .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.ALREADY_COMPLETED_BIDDING_PROCESS);
        }
    }
}