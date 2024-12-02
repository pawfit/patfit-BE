package bidding;

import com.peauty.domain.bidding.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("입찰 시나리오 테스트")
class BiddingScenarioTest {

    private PuppyId puppyId;
    private DesignerId designerId;
    private BiddingProcess process;
    private BiddingThread.ID thread1Id;
    private BiddingThread.ID thread2Id;
    private BiddingThread.ID thread3Id;
    private BiddingThreadTimeInfo threadTimeInfo;
    private BiddingProcessTimeInfo processTimeInfo;

    @BeforeEach
    void setUp() {
        puppyId = new PuppyId(1L);
        designerId = new DesignerId(1L);
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
    @DisplayName("한 스레드가 예약 상태가 되면 다른 스레드들이 대기 상태로 변경된다")
    void changeOtherThreadsToWaitingWhenOneThreadReserved() {
        // when
        process.responseEstimateThread(thread1Id); // ESTIMATE_REQUEST -> ESTIMATE_RESPONSE
        process.reserveThread(thread1Id);          // ESTIMATE_RESPONSE -> RESERVED

        // then
        // 첫 번째 스레드는 RESERVED 상태
        BiddingThread firstThread = process.getThread(thread1Id);
        assertEquals(BiddingThreadStep.RESERVED, firstThread.getStep());

        // 나머지 스레드들은 WAITING 상태로 변경
        BiddingThread secondThread = process.getThread(thread2Id);
        BiddingThread thirdThread = process.getThread(thread3Id);

        assertEquals(BiddingThreadStatus.WAITING, secondThread.getStatus());
        assertEquals(BiddingThreadStatus.WAITING, thirdThread.getStatus());

        // 프로세스는 RESERVED 상태
        assertEquals(BiddingProcessStatus.RESERVED, process.getStatus());
    }

    @Test
    @DisplayName("예약된 스레드가 취소되면 다른 스레드들이 진행중 상태로 변경된다")
    void changeWaitingThreadsToOngoingWhenReservedThreadCanceled() {
        // given
        process.responseEstimateThread(thread1Id); // ESTIMATE_REQUEST -> ESTIMATE_RESPONSE
        process.reserveThread(thread1Id);          // ESTIMATE_RESPONSE -> RESERVED

        // when
        process.cancelThread(thread1Id);

        // then
        // 예약됐던 스레드는 취소 상태
        BiddingThread firstThread = process.getThread(thread1Id);
        assertEquals(BiddingThreadStatus.CANCELED, firstThread.getStatus());

        // 나머지 스레드들은 다시 NORMAL 상태로 변경
        BiddingThread secondThread = process.getThread(thread2Id);
        BiddingThread thirdThread = process.getThread(thread3Id);

        assertEquals(BiddingThreadStatus.NORMAL, secondThread.getStatus());
        assertEquals(BiddingThreadStatus.NORMAL, thirdThread.getStatus());

        // 프로세스는 RESERVED_YET 상태로 변경
        assertEquals(BiddingProcessStatus.RESERVED_YET, process.getStatus());
    }

    @Test
    @DisplayName("예약 단계로 진행 시 취소된 스레드를 제외한 나머지 스레드들만 대기 상태로 변경된다")
    void changeOnlyActiveThreadsToWaitingWhenReserved() {
        // given
        process.cancelThread(thread3Id);  // 3번 스레드 취소
        process.responseEstimateThread(thread1Id);  // 1번 스레드 견적 응답

        // when
        process.reserveThread(thread1Id);  // 1번 스레드 예약

        // then
        // 1번 스레드는 예약 상태
        BiddingThread thread1 = process.getThread(thread1Id);
        assertEquals(BiddingThreadStep.RESERVED, thread1.getStep());
        assertEquals(BiddingThreadStatus.NORMAL, thread1.getStatus());

        // 2번 스레드는 대기 상태
        BiddingThread thread2 = process.getThread(thread2Id);
        assertEquals(BiddingThreadStep.ESTIMATE_REQUEST, thread2.getStep());
        assertEquals(BiddingThreadStatus.WAITING, thread2.getStatus());

        // 3번 스레드는 여전히 취소 상태
        BiddingThread thread3 = process.getThread(thread3Id);
        assertEquals(BiddingThreadStep.ESTIMATE_REQUEST, thread3.getStep());
        assertEquals(BiddingThreadStatus.CANCELED, thread3.getStatus());

        // 프로세스는 예약 상태
        assertEquals(BiddingProcessStatus.RESERVED, process.getStatus());

        // TimeInfo 검증
        verify(threadTimeInfo, times(2)).onStepChange();  // 1번 스레드의 견적응답, 예약
        verify(threadTimeInfo, times(2)).onStatusChange(); // 2번 스레드의 대기, 3번 스레드의 취소
        verify(processTimeInfo).onStatusChange();  // 프로세스의 예약
    }

    @Test
    @DisplayName("예약된 스레드 취소 시 취소된 스레드를 제외한 나머지 스레드들만 진행중 상태로 변경된다")
    void changeOnlyWaitingThreadsToOngoingWhenReservedThreadCanceled() {
        // given
        process.cancelThread(thread3Id);  // 3번 스레드 취소
        process.responseEstimateThread(thread1Id);  // 1번 스레드 견적 응답
        process.reserveThread(thread1Id);  // 1번 스레드 예약

        // 이 시점에서 2번만 WAITING 상태

        // when
        process.cancelThread(thread1Id);  // 예약된 1번 스레드 취소

        // then
        // 1번 스레드는 취소 상태
        BiddingThread thread1 = process.getThread(thread1Id);
        assertEquals(BiddingThreadStep.RESERVED, thread1.getStep());
        assertEquals(BiddingThreadStatus.CANCELED, thread1.getStatus());

        // 2번 스레드는 다시 진행중 상태
        BiddingThread thread2 = process.getThread(thread2Id);
        assertEquals(BiddingThreadStep.ESTIMATE_REQUEST, thread2.getStep());
        assertEquals(BiddingThreadStatus.NORMAL, thread2.getStatus());

        // 3번 스레드는 여전히 취소 상태
        BiddingThread thread3 = process.getThread(thread3Id);
        assertEquals(BiddingThreadStep.ESTIMATE_REQUEST, thread3.getStep());
        assertEquals(BiddingThreadStatus.CANCELED, thread3.getStatus());

        // 프로세스는 예약 전 상태
        assertEquals(BiddingProcessStatus.RESERVED_YET, process.getStatus());

        // TimeInfo 검증
        verify(threadTimeInfo, times(2)).onStepChange();  // 1번 스레드의 견적응답, 예약
        verify(threadTimeInfo, times(4)).onStatusChange(); // 2번의 대기->진행중, 1번과 3번의 취소
        verify(processTimeInfo, times(2)).onStatusChange();  // 프로세스의 예약->예약전
    }
}
