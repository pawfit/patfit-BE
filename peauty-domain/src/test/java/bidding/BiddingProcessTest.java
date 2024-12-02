package bidding;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.bidding.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BiddingProcessTest {

    private PuppyId puppyId;
    private DesignerId designerId;

    @BeforeEach
    void setUp() {
        puppyId = new PuppyId(1L);
        designerId = new DesignerId(1L);
    }

    @Nested
    @DisplayName("프로세스 생성 테스트")
    class CreateTest {

        @Test
        @DisplayName("새로운 프로세스를 생성할 수 있다")
        void createNewProcess() {
            // when
            BiddingProcess process = BiddingProcess.createNewProcess(puppyId);

            // then
            assertEquals(puppyId, process.getPuppyId());
            assertNull(process.getId().orElse(null));
            assertEquals(BiddingProcessStatus.RESERVED_YET, process.getStatus());
            assertTrue(process.getThreads().isEmpty());
        }

        @Test
        @DisplayName("디자이너 ID와 함께 새로운 프로세스를 생성할 수 있다")
        void createNewProcessWithDesigner() {
            // when
            BiddingProcess process = BiddingProcess.createNewProcess(puppyId, designerId);

            // then
            assertEquals(puppyId, process.getPuppyId());
            assertEquals(1, process.getThreads().size());
            assertEquals(designerId, process.getThreads().get(0).getDesignerId());
        }

        @Test
        @DisplayName("기존 프로세스를 로드할 수 있다")
        void loadProcess() {
            // given
            BiddingProcess.ID processId = new BiddingProcess.ID(1L);
            List<BiddingThread> threads = new ArrayList<>();

            // when
            BiddingProcess process = BiddingProcess.loadProcess(
                    processId,
                    puppyId,
                    BiddingProcessStatus.RESERVED_YET,
                    BiddingProcessTimeInfo.createNewTimeInfo(),
                    threads
            );

            // then
            assertEquals(processId, process.getId().orElse(null));
            assertEquals(puppyId, process.getPuppyId());
        }
    }

    @Nested
    @DisplayName("스레드 추가 테스트")
    class AddThreadTest {
        private BiddingProcess process;

        @BeforeEach
        void setUp() {
            process = BiddingProcess.createNewProcess(puppyId);
        }

        @Test
        @DisplayName("프로세스에 새로운 스레드를 추가할 수 있다")
        void addNewThread() {
            // when
            process.addNewThread(designerId);

            // then
            assertEquals(1, process.getThreads().size());
            BiddingThread addedThread = process.getThread(designerId);
            assertEquals(designerId, addedThread.getDesignerId());
        }

        @Test
        @DisplayName("이미 취소된 프로세스에는 스레드를 추가할 수 없다")
        void cannotAddThreadToCanceledProcess() {
            // given
            process = BiddingProcess.loadProcess(
                    null,
                    puppyId,
                    BiddingProcessStatus.CANCELED,
                    BiddingProcessTimeInfo.createNewTimeInfo(),
                    new ArrayList<>()
            );

            // when & then
            assertThrows(PeautyException.class, () -> process.addNewThread(designerId));
        }

        @Test
        @DisplayName("이미 완료된 프로세스에는 스레드를 추가할 수 없다")
        void cannotAddThreadToCompletedProcess() {
            // given
            process = BiddingProcess.loadProcess(
                    null,
                    puppyId,
                    BiddingProcessStatus.COMPLETED,
                    BiddingProcessTimeInfo.createNewTimeInfo(),
                    new ArrayList<>()
            );

            // when & then
            assertThrows(PeautyException.class, () -> process.addNewThread(designerId));
        }

        @Test
        @DisplayName("같은 디자이너의 스레드는 중복해서 추가할 수 없다")
        void cannotAddDuplicateDesignerThread() {
            // given
            process.addNewThread(designerId);

            // when & then
            assertThrows(PeautyException.class, () -> process.addNewThread(designerId));
        }
    }

    @Nested
    @DisplayName("스레드 조회 테스트")
    class GetThreadTest {
        private BiddingProcess process;
        private BiddingThread thread;

        @BeforeEach
        void setUp() {
            // 스레드 생성
            thread = BiddingThread.loadThread(
                    new BiddingThread.ID(1L),
                    puppyId,
                    designerId,
                    BiddingThreadStep.ESTIMATE_REQUEST,
                    BiddingThreadStatus.NORMAL,
                    BiddingThreadTimeInfo.createNewTimeInfo()
            );

            List<BiddingThread> threads = List.of(thread);

            // 프로세스 생성
            process = BiddingProcess.loadProcess(
                    new BiddingProcess.ID(1L),
                    puppyId,
                    BiddingProcessStatus.RESERVED_YET,
                    BiddingProcessTimeInfo.createNewTimeInfo(),
                    threads
            );
        }

        @Test
        @DisplayName("ID로 스레드를 조회할 수 있다")
        void getThreadById() {
            // when
            BiddingThread found = process.getThread(thread.getId());

            // then
            assertEquals(thread.getId(), found.getId());
        }

        @Test
        @DisplayName("디자이너 ID로 스레드를 조회할 수 있다")
        void getThreadByDesignerId() {
            // when
            BiddingThread found = process.getThread(designerId);

            // then
            assertEquals(designerId, found.getDesignerId());
        }

        @Test
        @DisplayName("존재하지 않는 스레드 ID로 조회시 예외가 발생한다")
        void throwExceptionWhenThreadNotFound() {
            // when & then
            assertThrows(PeautyException.class, () -> process.getThread(new BiddingThread.ID(999L)));
        }

        @Test
        @DisplayName("존재하지 않는 디자이너 ID로 조회시 예외가 발생한다")
        void throwExceptionWhenDesignerNotFound() {
            // when & then
            assertThrows(PeautyException.class,
                    () -> process.getThread(new DesignerId(999L)));
        }
    }

    @Nested
    @DisplayName("프로세스 취소 테스트")
    class ProcessCancelTest {
        private BiddingProcess process;
        private BiddingThreadTimeInfo threadTimeInfo;
        private BiddingProcessTimeInfo processTimeInfo;

        @BeforeEach
        void setUp() {
            // TimeInfo mocking
            threadTimeInfo = mock(BiddingThreadTimeInfo.class);
            processTimeInfo = mock(BiddingProcessTimeInfo.class);

            // 스레드 준비
            BiddingThread thread = BiddingThread.loadThread(
                    new BiddingThread.ID(1L),
                    puppyId,
                    new DesignerId(1L),
                    BiddingThreadStep.ESTIMATE_REQUEST,
                    BiddingThreadStatus.NORMAL,
                    threadTimeInfo
            );

            List<BiddingThread> threads = List.of(thread);

            // 프로세스 생성
            process = BiddingProcess.loadProcess(
                    new BiddingProcess.ID(1L),
                    puppyId,
                    BiddingProcessStatus.RESERVED_YET,
                    processTimeInfo,
                    threads
            );
        }

        @Test
        @DisplayName("프로세스를 취소할 수 있다")
        void cancelProcess() {
            // when
            process.cancel();

            // then
            assertEquals(BiddingProcessStatus.CANCELED, process.getStatus());
            verify(processTimeInfo).onStatusChange();
        }

        @Test
        @DisplayName("이미 취소된 프로세스는 다시 취소할 수 없다")
        void cannotCancelCanceledProcess() {
            // given
            process.cancel();

            // when & then
            assertThrows(PeautyException.class, () -> process.cancel());
        }

        @Test
        @DisplayName("취소된 프로세스의 스레드는 취소할 수 없다")
        void cannotCancelThreadInCanceledProcess() {
            // given
            process.cancel();

            // when & then
            assertThrows(PeautyException.class, () -> process.cancelThread(new BiddingThread.ID(1L)));
        }
    }

    @Nested
    @DisplayName("스레드 진행 테스트")
    class ThreadProgressTest {
        private BiddingProcess process;
        private BiddingThread.ID thread1Id;
        private BiddingThread.ID thread2Id;
        private BiddingThread.ID thread3Id;
        private BiddingThreadTimeInfo threadTimeInfo;
        private BiddingProcessTimeInfo processTimeInfo;

        @BeforeEach
        void setUp() {
            // TimeInfo mocking
            threadTimeInfo = mock(BiddingThreadTimeInfo.class);
            processTimeInfo = mock(BiddingProcessTimeInfo.class);

            // 세 개의 스레드 준비
            BiddingThread thread1 = BiddingThread.loadThread(
                    new BiddingThread.ID(1L),
                    puppyId,
                    new DesignerId(1L),
                    BiddingThreadStep.ESTIMATE_REQUEST,
                    BiddingThreadStatus.NORMAL,
                    threadTimeInfo
            );

            BiddingThread thread2 = BiddingThread.loadThread(
                    new BiddingThread.ID(2L),
                    puppyId,
                    new DesignerId(2L),
                    BiddingThreadStep.ESTIMATE_REQUEST,
                    BiddingThreadStatus.NORMAL,
                    threadTimeInfo
            );

            BiddingThread thread3 = BiddingThread.loadThread(
                    new BiddingThread.ID(3L),
                    puppyId,
                    new DesignerId(3L),
                    BiddingThreadStep.ESTIMATE_REQUEST,
                    BiddingThreadStatus.NORMAL,
                    threadTimeInfo
            );

            thread1Id = thread1.getId();
            thread2Id = thread2.getId();
            thread3Id = thread3.getId();

            List<BiddingThread> threads = List.of(thread1, thread2, thread3);

            // 프로세스 생성
            process = BiddingProcess.loadProcess(
                    new BiddingProcess.ID(1L),
                    puppyId,
                    BiddingProcessStatus.RESERVED_YET,
                    processTimeInfo,
                    threads
            );
        }


        @Test
        @DisplayName("견적 응답으로 진행할 수 있다")
        void responseEstimate() {
            // when
            process.responseEstimateThread(thread1Id);

            // then
            BiddingThread thread = process.getThread(thread1Id);
            assertEquals(BiddingThreadStep.ESTIMATE_RESPONSE, thread.getStep());
            assertEquals(BiddingProcessStatus.RESERVED_YET, process.getStatus());
            verify(threadTimeInfo).onStepChange();
        }

        @Test
        @DisplayName("예약 단계로 진행할 수 있다")
        void reserve() {
            // given
            process.responseEstimateThread(thread1Id);

            // when
            process.reserveThread(thread1Id);

            // then
            BiddingThread thread = process.getThread(thread1Id);
            assertEquals(BiddingThreadStep.RESERVED, thread.getStep());
            assertEquals(BiddingProcessStatus.RESERVED, process.getStatus());
            verify(threadTimeInfo, times(2)).onStepChange();
            verify(processTimeInfo).onStatusChange();
        }

        @Test
        @DisplayName("완료 단계로 진행할 수 있다")
        void complete() {
            // given
            process.responseEstimateThread(thread1Id);
            process.reserveThread(thread1Id);

            // when
            process.completeThread(thread1Id);

            // then
            BiddingThread thread = process.getThread(thread1Id);
            assertEquals(BiddingThreadStep.COMPLETED, thread.getStep());
            assertEquals(BiddingProcessStatus.COMPLETED, process.getStatus());
            verify(threadTimeInfo, times(3)).onStepChange();
            verify(processTimeInfo, times(2)).onStatusChange();
        }

        @Test
        @DisplayName("취소된 프로세스는 스레드를 진행할 수 없다")
        void cannotProgressThreadInCanceledProcess() {
            // given
            process.cancel();

            // when & then
            assertThrows(PeautyException.class, () -> process.responseEstimateThread(thread1Id));
        }

        @Test
        @DisplayName("완료된 프로세스는 스레드를 진행할 수 없다")
        void cannotProgressThreadInCompletedProcess() {
            // given
            process.responseEstimateThread(thread1Id);
            process.reserveThread(thread1Id);
            process.completeThread(thread1Id);

            // when & then
            assertThrows(PeautyException.class, () -> process.responseEstimateThread(thread2Id));
        }

        @Test
        @DisplayName("잘못된 순서로 스레드를 진행할 수 없다")
        void cannotProgressThreadInWrongOrder() {
            // when & then
            assertThrows(PeautyException.class, () -> process.reserveThread(thread1Id));
        }

        @Test
        @DisplayName("디자이너 ID로도 스레드를 진행할 수 있다")
        void progressThreadByDesignerId() {
            // when
            process.responseEstimateThread(new DesignerId(1L));

            // then
            BiddingThread thread = process.getThread(thread1Id);
            assertEquals(BiddingThreadStep.ESTIMATE_RESPONSE, thread.getStep());
            verify(threadTimeInfo).onStepChange();
        }
    }
}