package bidding;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.bidding.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BiddingThreadTest {

    private BiddingProcess mockProcess;
    private PuppyId puppyId;
    private DesignerId designerId;

    @BeforeEach
    void setUp() {
        mockProcess = mock(BiddingProcess.class);
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
            BiddingThread thread = BiddingThread.createNewThread(puppyId, designerId);

            // then
            assertThat(thread.getId()).isNull();
            assertThat(thread.getPuppyId()).isEqualTo(puppyId);
            assertThat(thread.getDesignerId()).isEqualTo(designerId);
            assertThat(thread.getStep()).isEqualTo(BiddingThreadStep.ESTIMATE_REQUEST);
        }

        @Test
        @DisplayName("기존 스레드를 로드할 수 있다")
        void loadThread() {
            // given
            BiddingThread.ID threadId = new BiddingThread.ID(1L);
            BiddingThreadStep step = BiddingThreadStep.ESTIMATE_RESPONSE;
            BiddingThreadStatus status = BiddingThreadStatus.NORMAL;
            BiddingThreadTimeInfo timeInfo = mock(BiddingThreadTimeInfo.class);

            // when
            BiddingThread thread = BiddingThread.loadThread(
                    threadId, puppyId, designerId, step, status, timeInfo);

            // then
            assertThat(thread.getId()).isEqualTo(threadId);
            assertThat(thread.getStep()).isEqualTo(step);
        }
    }

    @Nested
    @DisplayName("스레드 단계 진행 테스트")
    class ProgressStepTest {
        private BiddingThread thread;
        private BiddingThreadTimeInfo mockTimeInfo;

        @BeforeEach
        void setUp() {
            mockTimeInfo = mock(BiddingThreadTimeInfo.class);
            thread = BiddingThread.loadThread(
                    new BiddingThread.ID(1L),
                    puppyId,
                    designerId,
                    BiddingThreadStep.ESTIMATE_REQUEST,
                    BiddingThreadStatus.NORMAL,
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
            assertThat(thread.getStep()).isEqualTo(BiddingThreadStep.ESTIMATE_RESPONSE);
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
            assertThat(thread.getStep()).isEqualTo(BiddingThreadStep.RESERVED);
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
            assertThat(thread.getStep()).isEqualTo(BiddingThreadStep.COMPLETED);
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
        private BiddingThread thread;
        private BiddingThreadTimeInfo mockTimeInfo;

        @BeforeEach
        void setUp() {
            mockTimeInfo = mock(BiddingThreadTimeInfo.class);
            thread = BiddingThread.loadThread(
                    new BiddingThread.ID(1L),
                    puppyId,
                    designerId,
                    BiddingThreadStep.ESTIMATE_REQUEST,
                    BiddingThreadStatus.NORMAL,
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
            assertThat(thread.getStatus()).isEqualTo(BiddingThreadStatus.CANCELED);
            verify(mockTimeInfo).onStatusChange();
        }

        @Test
        @DisplayName("예약된 스레드를 취소하면 프로세스에 통지한다")
        void notifyProcessWhenReservedThreadCanceled() {
            // given
            thread = BiddingThread.loadThread(
                    new BiddingThread.ID(1L),
                    puppyId,
                    designerId,
                    BiddingThreadStep.RESERVED,
                    BiddingThreadStatus.NORMAL,
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
            thread = BiddingThread.loadThread(
                    new BiddingThread.ID(1L),
                    puppyId,
                    designerId,
                    BiddingThreadStep.ESTIMATE_REQUEST,
                    BiddingThreadStatus.CANCELED,
                    mockTimeInfo
            );

            // when & then
            assertThrows(PeautyException.class, () -> thread.cancel());
        }

        @Test
        @DisplayName("완료된 스레드는 취소할 수 없다")
        void cannotCancelCompletedThread() {
            // given
            thread = BiddingThread.loadThread(
                    new BiddingThread.ID(1L),
                    puppyId,
                    designerId,
                    BiddingThreadStep.COMPLETED,
                    BiddingThreadStatus.NORMAL,
                    mockTimeInfo
            );

            // when & then
            assertThrows(PeautyException.class, () -> thread.cancel());
        }
    }

    @Nested
    @DisplayName("스레드 상태 변경 테스트")
    class StatusChangeTest {
        private BiddingThread thread;
        private BiddingThreadTimeInfo mockTimeInfo;

        @BeforeEach
        void setUp() {
            mockTimeInfo = mock(BiddingThreadTimeInfo.class);
            thread = BiddingThread.loadThread(
                    new BiddingThread.ID(1L),
                    puppyId,
                    designerId,
                    BiddingThreadStep.ESTIMATE_REQUEST,
                    BiddingThreadStatus.NORMAL,
                    mockTimeInfo
            );
        }

        @Test
        @DisplayName("진행 중인 스레드를 대기 상태로 변경할 수 있다")
        void canChangeToWaiting() {
            // when
            thread.waiting();

            // then
            assertThat(thread.getStatus()).isEqualTo(BiddingThreadStatus.WAITING);
            verify(mockTimeInfo).onStatusChange();
        }

        @Test
        @DisplayName("대기 중인 스레드를 진행 중 상태로 변경할 수 있다")
        void canRelease() {
            // given
            thread = BiddingThread.loadThread(
                    new BiddingThread.ID(1L),
                    puppyId,
                    designerId,
                    BiddingThreadStep.ESTIMATE_REQUEST,
                    BiddingThreadStatus.WAITING,
                    mockTimeInfo
            );

            // when
            thread.release();

            // then
            assertThat(thread.getStatus()).isEqualTo(BiddingThreadStatus.NORMAL);
            verify(mockTimeInfo).onStatusChange();
        }
    }
}