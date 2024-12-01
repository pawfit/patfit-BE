package grooming;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.grooming.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GroomingBiddingProcessTest {

    private CustomerId customerId;
    private DesignerId designerId;

    @BeforeEach
    void setUp() {
        customerId = new CustomerId(1L);
        designerId = new DesignerId(1L);
    }

    @Nested
    @DisplayName("프로세스 생성 테스트")
    class CreateTest {

        @Test
        @DisplayName("새로운 프로세스를 생성할 수 있다")
        void createNewProcess() {
            // when
            GroomingBiddingProcess process = GroomingBiddingProcess.createNewProcess(customerId);

            // then
            assertEquals(customerId, process.getCustomerId());
            assertNull(process.getId().orElse(null));
            assertEquals(GroomingBiddingProcessStatus.RESERVED_YET, process.getStatus());
            assertTrue(process.getThreads().isEmpty());
        }

        @Test
        @DisplayName("디자이너 ID와 함께 새로운 프로세스를 생성할 수 있다")
        void createNewProcessWithDesigner() {
            // when
            GroomingBiddingProcess process = GroomingBiddingProcess.createNewProcess(customerId, designerId);

            // then
            assertEquals(customerId, process.getCustomerId());
            assertEquals(1, process.getThreads().size());
            assertEquals(designerId, process.getThreads().get(0).getDesignerId());
        }

        @Test
        @DisplayName("기존 프로세스를 로드할 수 있다")
        void loadProcess() {
            // given
            GroomingBiddingProcess.ID processId = new GroomingBiddingProcess.ID(1L);
            List<GroomingBiddingThread> threads = new ArrayList<>();

            // when
            GroomingBiddingProcess process = GroomingBiddingProcess.loadProcess(
                    processId,
                    customerId,
                    GroomingBiddingProcessStatus.RESERVED_YET,
                    GroomingBiddingProcessTimeInfo.createNewTimeInfo(),
                    threads
            );

            // then
            assertEquals(processId, process.getId().orElse(null));
            assertEquals(customerId, process.getCustomerId());
        }
    }

    @Nested
    @DisplayName("스레드 추가 테스트")
    class AddThreadTest {
        private GroomingBiddingProcess process;

        @BeforeEach
        void setUp() {
            process = GroomingBiddingProcess.createNewProcess(customerId);
        }

        @Test
        @DisplayName("프로세스에 새로운 스레드를 추가할 수 있다")
        void addNewThread() {
            // when
            process.addNewThread(designerId);

            // then
            assertEquals(1, process.getThreads().size());
            GroomingBiddingThread addedThread = process.getThreadByDesignerId(designerId);
            assertEquals(designerId, addedThread.getDesignerId());
        }

        @Test
        @DisplayName("이미 취소된 프로세스에는 스레드를 추가할 수 없다")
        void cannotAddThreadToCanceledProcess() {
            // given
            process = GroomingBiddingProcess.loadProcess(
                    null,
                    customerId,
                    GroomingBiddingProcessStatus.CANCELED,
                    GroomingBiddingProcessTimeInfo.createNewTimeInfo(),
                    new ArrayList<>()
            );

            // when & then
            assertThrows(PeautyException.class, () -> process.addNewThread(designerId));
        }

        @Test
        @DisplayName("이미 완료된 프로세스에는 스레드를 추가할 수 없다")
        void cannotAddThreadToCompletedProcess() {
            // given
            process = GroomingBiddingProcess.loadProcess(
                    null,
                    customerId,
                    GroomingBiddingProcessStatus.COMPLETED,
                    GroomingBiddingProcessTimeInfo.createNewTimeInfo(),
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
        private GroomingBiddingProcess process;
        private GroomingBiddingThread thread;

        @BeforeEach
        void setUp() {
            // 스레드 생성
            thread = GroomingBiddingThread.loadThread(
                    new GroomingBiddingThread.ID(1L),
                    customerId,
                    designerId,
                    GroomingBiddingThreadStep.ESTIMATE_REQUEST,
                    GroomingBiddingThreadStatus.ONGOING,
                    GroomingBiddingThreadTimeInfo.createNewTimeInfo()
            );

            List<GroomingBiddingThread> threads = List.of(thread);

            // 프로세스 생성
            process = GroomingBiddingProcess.loadProcess(
                    new GroomingBiddingProcess.ID(1L),
                    customerId,
                    GroomingBiddingProcessStatus.RESERVED_YET,
                    GroomingBiddingProcessTimeInfo.createNewTimeInfo(),
                    threads
            );
        }

        @Test
        @DisplayName("ID로 스레드를 조회할 수 있다")
        void getThreadById() {
            // when
            GroomingBiddingThread found = process.getThreadByThreadId(thread.getId());

            // then
            assertEquals(thread.getId(), found.getId());
        }

        @Test
        @DisplayName("디자이너 ID로 스레드를 조회할 수 있다")
        void getThreadByDesignerId() {
            // when
            GroomingBiddingThread found = process.getThreadByDesignerId(designerId);

            // then
            assertEquals(designerId, found.getDesignerId());
        }

        @Test
        @DisplayName("존재하지 않는 스레드 ID로 조회시 예외가 발생한다")
        void throwExceptionWhenThreadNotFound() {
            // when & then
            assertThrows(PeautyException.class, () -> process.getThreadByThreadId(new GroomingBiddingThread.ID(999L)));
        }

        @Test
        @DisplayName("존재하지 않는 디자이너 ID로 조회시 예외가 발생한다")
        void throwExceptionWhenDesignerNotFound() {
            // when & then
            assertThrows(PeautyException.class,
                    () -> process.getThreadByDesignerId(new DesignerId(999L)));
        }
    }

    @Nested
    @DisplayName("프로세스 상태 변경 테스트")
    class StatusChangeTest {
        private GroomingBiddingProcess process;
        private GroomingBiddingThread.ID thread1Id;
        private GroomingBiddingThread.ID thread2Id;
        private GroomingBiddingThread.ID thread3Id;
        private GroomingBiddingThreadTimeInfo threadTimeInfo;
        private GroomingBiddingProcessTimeInfo processTimeInfo;

        @BeforeEach
        void setUp() {
            threadTimeInfo = mock(GroomingBiddingThreadTimeInfo.class);
            processTimeInfo = mock(GroomingBiddingProcessTimeInfo.class);
            // 세 개의 스레드 준비
            GroomingBiddingThread thread1 = GroomingBiddingThread.loadThread(
                    new GroomingBiddingThread.ID(1L),
                    customerId,
                    new DesignerId(1L),
                    GroomingBiddingThreadStep.ESTIMATE_REQUEST,
                    GroomingBiddingThreadStatus.ONGOING,
                    threadTimeInfo
            );

            GroomingBiddingThread thread2 = GroomingBiddingThread.loadThread(
                    new GroomingBiddingThread.ID(2L),
                    customerId,
                    new DesignerId(2L),
                    GroomingBiddingThreadStep.ESTIMATE_REQUEST,
                    GroomingBiddingThreadStatus.ONGOING,
                    threadTimeInfo
            );

            GroomingBiddingThread thread3 = GroomingBiddingThread.loadThread(
                    new GroomingBiddingThread.ID(3L),
                    customerId,
                    new DesignerId(3L),
                    GroomingBiddingThreadStep.ESTIMATE_REQUEST,
                    GroomingBiddingThreadStatus.ONGOING,
                    threadTimeInfo
            );

            thread1Id = thread1.getId();
            thread2Id = thread2.getId();
            thread3Id = thread3.getId();

            List<GroomingBiddingThread> threads = List.of(thread1, thread2, thread3);

            // 프로세스 생성
            process = GroomingBiddingProcess.loadProcess(
                    new GroomingBiddingProcess.ID(1L),
                    customerId,
                    GroomingBiddingProcessStatus.RESERVED_YET,
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
            assertEquals(GroomingBiddingProcessStatus.CANCELED, process.getStatus());
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
        @DisplayName("한 스레드가 예약 상태가 되면 다른 스레드들이 대기 상태로 변경된다")
        void changeOtherThreadsToWaitingWhenOneThreadReserved() {
            // when
            process.progressThreadStep(thread1Id); // ESTIMATE_REQUEST -> ESTIMATE_RESPONSE
            process.progressThreadStep(thread1Id); // ESTIMATE_RESPONSE -> RESERVED

            // then
            // 첫 번째 스레드는 RESERVED 상태
            GroomingBiddingThread firstThread = process.getThreadByThreadId(thread1Id);
            assertEquals(GroomingBiddingThreadStep.RESERVED, firstThread.getStep());

            // 나머지 스레드들은 WAITING 상태로 변경
            GroomingBiddingThread secondThread = process.getThreadByThreadId(thread2Id);
            GroomingBiddingThread thirdThread = process.getThreadByThreadId(thread3Id);

            assertEquals(GroomingBiddingThreadStatus.WAITING, secondThread.getStatus());
            assertEquals(GroomingBiddingThreadStatus.WAITING, thirdThread.getStatus());

            // 프로세스는 RESERVED 상태
            assertEquals(GroomingBiddingProcessStatus.RESERVED, process.getStatus());
        }

        @Test
        @DisplayName("예약된 스레드가 취소되면 다른 스레드들이 진행중 상태로 변경된다")
        void changeWaitingThreadsToOngoingWhenReservedThreadCanceled() {
            // given
            process.progressThreadStep(thread1Id); // ESTIMATE_REQUEST -> ESTIMATE_RESPONSE
            process.progressThreadStep(thread1Id); // ESTIMATE_RESPONSE -> RESERVED

            // when
            process.cancelThread(thread1Id);

            // then
            // 예약됐던 스레드는 취소 상태
            GroomingBiddingThread firstThread = process.getThreadByThreadId(thread1Id);
            assertEquals(GroomingBiddingThreadStatus.CANCELED, firstThread.getStatus());

            // 나머지 스레드들은 다시 ONGOING 상태로 변경
            GroomingBiddingThread secondThread = process.getThreadByThreadId(thread2Id);
            GroomingBiddingThread thirdThread = process.getThreadByThreadId(thread3Id);

            assertEquals(GroomingBiddingThreadStatus.ONGOING, secondThread.getStatus());
            assertEquals(GroomingBiddingThreadStatus.ONGOING, thirdThread.getStatus());

            // 프로세스는 RESERVED_YET 상태로 변경
            assertEquals(GroomingBiddingProcessStatus.RESERVED_YET, process.getStatus());
        }
    }
}