package com.peauty.designer.business.bidding;

import com.peauty.designer.business.bidding.dto.*;
import com.peauty.designer.business.designer.DesignerPort;
import com.peauty.designer.business.puppy.PuppyPort;
import com.peauty.designer.business.workspace.WorkspacePort;
import com.peauty.domain.bidding.*;
import com.peauty.domain.puppy.Puppy;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DesignerBiddingServiceImpl implements DesignerBiddingService {

    private final BiddingProcessPort biddingProcessPort;
    private final EstimateProposalPort estimateProposalPort;
    private final EstimatePort estimatePort;
    private final PuppyPort puppyPort;
    private final DesignerPort designerPort;
    private final WorkspacePort workspacePort;

    @Override
    @Transactional
    public SendEstimateResult sendEstimate(
            Long userId,
            Long processId,
            Long threadId,
            SendEstimateCommand command
    ) {
        BiddingProcess process = biddingProcessPort.getProcessByProcessId(processId);
        process.responseEstimateThread(new BiddingThread.ID(threadId));
        BiddingThread responseEstimateThread = biddingProcessPort.save(process)
                .getThread(new BiddingThread.ID(threadId));
        Estimate newEstimate = command.toEstimate(responseEstimateThread.getSavedThreadId().value());
        Estimate savedEstimate = estimatePort.save(newEstimate);
        return SendEstimateResult.from(savedEstimate);
    }

    @Override
    @Transactional
    public CompleteGroomingResult completeGrooming(
            Long userId,
            Long processId,
            Long threadId
    ) {
        BiddingProcess process = biddingProcessPort.getProcessByProcessId(processId);
        process.completeThread(new BiddingThread.ID(threadId));
        BiddingProcess savedProcess = biddingProcessPort.save(process);
        BiddingThread completedThread = savedProcess.getThread(new BiddingThread.ID(threadId));
        return CompleteGroomingResult.from(savedProcess, completedThread);
    }

    @Override
    public GetEstimateAndProposalDetailsResult getEstimateAndProposalDetails(Long userId, Long processId, Long threadId) {
        BiddingProcess process = biddingProcessPort.getProcessByProcessId(processId);
        BiddingThread thread = process.getThread(new BiddingThread.ID(threadId));
        EstimateProposal estimateProposal = estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value());
        Optional<Estimate> estimate = estimatePort.findEstimateByThreadId(thread.getSavedThreadId().value());
        Puppy puppy = puppyPort.getPuppyByPuppyId(process.getPuppyId().value());
        return GetEstimateAndProposalDetailsResult.from(
                process,
                thread,
                puppy,
                estimateProposal,
                estimate
        );
    }

    // TODO 말도 안되는 쿼리가 날라가는데 반드시 고쳐야합니다..
    @Override
    public GetDesignerScheduleResult getDesignerSchedule(Long userId) {
        AtomicInteger completedCount = new AtomicInteger(0);
        AtomicInteger todayCount = new AtomicInteger(0);
        AtomicInteger futureCount = new AtomicInteger(0);

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        DesignerId designerId = new DesignerId(userId);

        return GetDesignerScheduleResult.from(
                designerPort.getByDesignerId(userId).getProfile(workspacePort.getByDesignerId(userId)),
                biddingProcessPort.getProcessesByDesignerId(userId)
                        .stream()
                        .filter(process -> process.getThread(designerId).getStep().isAfter(BiddingThreadStep.ESTIMATE_RESPONSE))
                        .peek(process -> {
                            BiddingThread thread = process.getThread(designerId);
                            BiddingThreadStep step = thread.getStep();

                            if (step == BiddingThreadStep.COMPLETED) {
                                completedCount.incrementAndGet();
                            }

                            if (step.isAfter(BiddingThreadStep.ESTIMATE_RESPONSE)) {
                                String desiredDateTime = estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value()).getDesiredDateTime();

                                // TODO LocalDateTime 포맷을 가진 값 객체 도입이 시급
                                if (desiredDateTime.startsWith(today)) {
                                    todayCount.incrementAndGet();
                                } else if (desiredDateTime.compareTo(today) > 0) {
                                    futureCount.incrementAndGet();
                                }
                            }
                        })
                        .filter(process -> estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value())
                                .getDesiredDateTime().startsWith(today))
                        .map(process -> process.getProfile(
                                puppyPort.getPuppyByPuppyId(process.getPuppyId().value()).getProfile(),
                                estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value()).getProfile(),
                                designerId,
                                estimatePort.getEstimateByThreadId(process.getThread(designerId).getSavedThreadId().value()).getProfile()
                        ))
                        .toList(),
                completedCount.get(), todayCount.get(), futureCount.get()
        );
    }

    @Override
    public GetThreadsByStepResult getStep3AboveThreads(Long userId) {
        return GetThreadsByStepResult.from(
                biddingProcessPort.getProcessesByDesignerId(userId)
                        .stream()
                        .filter(process -> process.getThread(new DesignerId(userId)).getStep().isAfter(BiddingThreadStep.ESTIMATE_RESPONSE))
                        .map(process -> process.getProfile(
                                puppyPort.getPuppyByPuppyId(process.getPuppyId().value()).getProfile(),
                                estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value()).getProfile(),
                                new DesignerId(userId)
                        ))
                        .toList()
        );
    }

    @Override
    public GetThreadsByStepResult getStep2Threads(Long userId) {
        return GetThreadsByStepResult.from(
                biddingProcessPort.getProcessesByDesignerId(userId)
                        .stream()
                        .filter(process -> process.getThread(new DesignerId(userId)).getStep().isEstimateResponse())
                        .map(process -> process.getProfile(
                                puppyPort.getPuppyByPuppyId(process.getPuppyId().value()).getProfile(),
                                estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value()).getProfile(),
                                new DesignerId(userId)
                        ))
                        .toList()
        );
    }

    @Override
    public GetThreadsByStepResult getStep1Threads(Long userId) {
        return GetThreadsByStepResult.from(
                biddingProcessPort.getProcessesByDesignerId(userId)
                        .stream()
                        .filter(process -> process.getThread(new DesignerId(userId)).getStep().isEstimateRequest())
                        .map(process -> process.getProfile(
                                puppyPort.getPuppyByPuppyId(process.getPuppyId().value()).getProfile(),
                                estimateProposalPort.getProposalByProcessId(process.getSavedProcessId().value()).getProfile(),
                                new DesignerId(userId)
                        ))
                        .toList()
        );
    }
}