package grooming;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.grooming.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GroomingBiddingThreadTest {

    private GroomingBiddingProcess mockProcess;
    private PuppyId puppyId;
    private DesignerId designerId;

    @BeforeEach
    void setUp() {
        mockProcess = mock(GroomingBiddingProcess.class);
        puppyId = new PuppyId(1L);
        designerId = new DesignerId(1L);
    }

    @Nested
    @DisplayName("스레드 생성 테스트")
    class CreateTest {
        @Test
        @DisplayName("새로운 스레드를 생성할 수 있다")
        void createNewThread() {
            // when
            GroomingBiddingThread thread = GroomingBiddingThread.createNewThread(puppyId, designerId);

            // then
            assertThat(thread.getId()).isNull();
            assertThat(thread.getPuppyId()).isEqualTo(puppyId);
            assertThat(thread.getDesignerId()).isEqualTo(designerId);
            assertThat(thread.getStep()).isEqualTo(GroomingBiddingThreadStep.ESTIMATE_REQUEST);
        }

        @Test
        @DisplayName("기존 스레드를 로드할 수 있다")
        void loadThread() {
            // given
            GroomingBiddingThread.ID threadId = new GroomingBiddingThread.ID(1L);
            GroomingBiddingThreadStep step = GroomingBiddingThreadStep.ESTIMATE_RESPONSE;
            GroomingBiddingThreadStatus status = GroomingBiddingThreadStatus.NORMAL;
            GroomingBiddingThreadTimeInfo timeInfo = mock(GroomingBiddingThreadTimeInfo.class);

            // when
            GroomingBiddingThread thread = GroomingBiddingThread.loadThread(
                    threadId, puppyId, designerId, step, status, timeInfo);

            // then
            assertThat(thread.getId()).isEqualTo(threadId);
            assertThat(thread.getStep()).isEqualTo(step);
        }
    }

    @Nested
    @DisplayName("스레드 단계 진행 테스트")
    class ProgressStepTest {
        private GroomingBiddingThread thread;
        private GroomingBiddingThreadTimeInfo mockTimeInfo;

        @BeforeEach
        void setUp() {
            mockTimeInfo = mock(GroomingBiddingThreadTimeInfo.class);
            thread = GroomingBiddingThread.loadThread(
                    new GroomingBiddingThread.ID(1L),
                    puppyId,
                    designerId,
                    GroomingBiddingThreadStep.ESTIMATE_REQUEST,
                    GroomingBiddingThreadStatus.NORMAL,
                    mockTimeInfo
            );
            thread.registerProcessObserver(mockProcess);
        }

        @Test
        @DisplayName("견적 응답 단계로 진행할 수 있다")
        void responseEstimate() {
            // when
            thread.responseEstimate();

            // then
            assertThat(thread.getStep()).isEqualTo(GroomingBiddingThreadStep.ESTIMATE_RESPONSE);
            verify(mockTimeInfo).onStepChange();
        }

        @Test
        @DisplayName("예약 단계로 진행할 수 있다")
        void reserve() {
            // given
            thread.responseEstimate();

            // when
            thread.reserve();

            // then
            assertThat(thread.getStep()).isEqualTo(GroomingBiddingThreadStep.RESERVED);
            verify(mockProcess).onThreadReserved();
            verify(mockTimeInfo, times(2)).onStepChange();
        }

        @Test
        @DisplayName("완료 단계로 진행할 수 있다")
        void complete() {
            // given
            thread.responseEstimate();
            thread.reserve();

            // when
            thread.complete();

            // then
            assertThat(thread.getStep()).isEqualTo(GroomingBiddingThreadStep.COMPLETED);
            verify(mockProcess).onThreadReserved();
            verify(mockProcess).onThreadCompleted();
            verify(mockTimeInfo, times(3)).onStepChange();
        }

        @Test
        @DisplayName("잘못된 순서로 단계를 진행할 수 없다")
        void cannotProgressInvalidOrder() {
            // given
            thread.responseEstimate();
            thread.reserve();

            // when & then
            assertThrows(PeautyException.class, () -> thread.reserve());
        }

        @Test
        @DisplayName("취소된 스레드는 단계를 진행할 수 없다")
        void cannotProgressCanceledThread() {
            // given
            thread.cancel();

            // when & then
            assertThrows(PeautyException.class, () -> thread.responseEstimate());
        }

        @Test
        @DisplayName("대기 중인 스레드는 단계를 진행할 수 없다")
        void cannotProgressWaitingThread() {
            // given
            thread.waiting();

            // when & then
            assertThrows(PeautyException.class, () -> thread.responseEstimate());
        }
    }

    @Nested
    @DisplayName("스레드 취소 테스트")
    class CancelTest {
        private GroomingBiddingThread thread;
        private GroomingBiddingThreadTimeInfo mockTimeInfo;

        @BeforeEach
        void setUp() {
            mockTimeInfo = mock(GroomingBiddingThreadTimeInfo.class);
            thread = GroomingBiddingThread.loadThread(
                    new GroomingBiddingThread.ID(1L),
                    puppyId,
                    designerId,
                    GroomingBiddingThreadStep.ESTIMATE_REQUEST,
                    GroomingBiddingThreadStatus.NORMAL,
                    mockTimeInfo
            );
            thread.registerProcessObserver(mockProcess);
        }

        @Test
        @DisplayName("스레드를 취소할 수 있다")
        void cancelThread() {
            // when
            thread.cancel();

            // then
            assertThat(thread.getStatus()).isEqualTo(GroomingBiddingThreadStatus.CANCELED);
            verify(mockTimeInfo).onStatusChange();
        }

        @Test
        @DisplayName("예약된 스레드를 취소하면 프로세스에 통지한다")
        void notifyProcessWhenReservedThreadCanceled() {
            // given
            thread = GroomingBiddingThread.loadThread(
                    new GroomingBiddingThread.ID(1L),
                    puppyId,
                    designerId,
                    GroomingBiddingThreadStep.RESERVED,
                    GroomingBiddingThreadStatus.NORMAL,
                    mockTimeInfo
            );
            thread.registerProcessObserver(mockProcess);

            // when
            thread.cancel();

            // then
            verify(mockProcess).onReservedThreadCancel();
        }

        @Test
        @DisplayName("이미 취소된 스레드는 다시 취소할 수 없다")
        void cannotCancelCanceledThread() {
            // given
            thread = GroomingBiddingThread.loadThread(
                    new GroomingBiddingThread.ID(1L),
                    puppyId,
                    designerId,
                    GroomingBiddingThreadStep.ESTIMATE_REQUEST,
                    GroomingBiddingThreadStatus.CANCELED,
                    mockTimeInfo
            );

            // when & then
            assertThrows(PeautyException.class, () -> thread.cancel());
        }

        @Test
        @DisplayName("완료된 스레드는 취소할 수 없다")
        void cannotCancelCompletedThread() {
            // given
            thread = GroomingBiddingThread.loadThread(
                    new GroomingBiddingThread.ID(1L),
                    puppyId,
                    designerId,
                    GroomingBiddingThreadStep.COMPLETED,
                    GroomingBiddingThreadStatus.NORMAL,
                    mockTimeInfo
            );

            // when & then
            assertThrows(PeautyException.class, () -> thread.cancel());
        }
    }

    @Nested
    @DisplayName("스레드 상태 변경 테스트")
    class StatusChangeTest {
        private GroomingBiddingThread thread;
        private GroomingBiddingThreadTimeInfo mockTimeInfo;

        @BeforeEach
        void setUp() {
            mockTimeInfo = mock(GroomingBiddingThreadTimeInfo.class);
            thread = GroomingBiddingThread.loadThread(
                    new GroomingBiddingThread.ID(1L),
                    puppyId,
                    designerId,
                    GroomingBiddingThreadStep.ESTIMATE_REQUEST,
                    GroomingBiddingThreadStatus.NORMAL,
                    mockTimeInfo
            );
        }

        @Test
        @DisplayName("진행 중인 스레드를 대기 상태로 변경할 수 있다")
        void canChangeToWaiting() {
            // when
            thread.waiting();

            // then
            assertThat(thread.getStatus()).isEqualTo(GroomingBiddingThreadStatus.WAITING);
            verify(mockTimeInfo).onStatusChange();
        }

        @Test
        @DisplayName("대기 중인 스레드를 진행 중 상태로 변경할 수 있다")
        void canRelease() {
            // given
            thread = GroomingBiddingThread.loadThread(
                    new GroomingBiddingThread.ID(1L),
                    puppyId,
                    designerId,
                    GroomingBiddingThreadStep.ESTIMATE_REQUEST,
                    GroomingBiddingThreadStatus.WAITING,
                    mockTimeInfo
            );

            // when
            thread.release();

            // then
            assertThat(thread.getStatus()).isEqualTo(GroomingBiddingThreadStatus.NORMAL);
            verify(mockTimeInfo).onStatusChange();
        }
    }
}