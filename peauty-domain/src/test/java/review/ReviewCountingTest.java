package review;

import com.peauty.domain.bidding.*;
import com.peauty.domain.designer.Workspace;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.review.ContentGeneral;
import com.peauty.domain.review.Review;
import com.peauty.domain.review.ReviewRating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("리뷰 카운팅 테스트")
class ReviewCountingTest {


    private static final Long WORKSPACE_ID = 1L;
    private static final Long DESIGNER_ID = 1L;
    private static final Long THREAD_ID = 1L;

    private Workspace workspace;
    private BiddingThread biddingThread;
    private Review review1;
    private Review review2;

    @BeforeEach
    void setUp() {
        // 워크스페이스 및 입찰 스레드 생성
        workspace = Workspace.builder()
                .workspaceId(WORKSPACE_ID)
                .designerId(DESIGNER_ID)
                .reviewCount(0)
                .reviewRating(0.0)
                .build();

        biddingThread = BiddingThread.loadThread(
                new BiddingThread.ID(THREAD_ID),
                new BiddingProcess.ID(1L),
                new DesignerId(DESIGNER_ID),
                BiddingThreadStep.ESTIMATE_REQUEST,
                BiddingThreadStatus.NORMAL,
                BiddingThreadTimeInfo.createNewTimeInfo()
        );

        review1 = Review.builder()
                .threadId(new BiddingThread.ID(THREAD_ID))
                .reviewRating(ReviewRating.FOUR) // 4점 리뷰
                .contentDetail("정말 친절하고 만족스러웠어요!")
                .contentGeneral(List.of(ContentGeneral.KIND))
                .build();

        review2 = Review.builder()
                .threadId(new BiddingThread.ID(THREAD_ID))
                .reviewRating(ReviewRating.FIVE) // 5점 리뷰
                .contentDetail("아주 만족스러웠습니다!")
                .contentGeneral(List.of(ContentGeneral.GOOD_SERVICE, ContentGeneral.KIND))
                .build();

    }

    @Test
    @DisplayName("리뷰 작성 시 평점이 워크스페이스에 반영된다")
    void reviewCreatesAndUpdatesWorkspaceRating() {
        // 리뷰 등록
        workspace.registerReviewStats(review1.getReviewRating());

        // 워크스페이스의 평점이 올바르게 반영되었는지 확인
        assertThat(workspace.getReviewCount()).isEqualTo(1);
        assertThat(workspace.getReviewRating()).isEqualTo(4.0); // 4점이 반영되어야 한다.
    }


    @Test
    @DisplayName("두 개의 리뷰가 작성되면 워크스페이스 평점이 올바르게 재계산된다")
    void multipleReviewsUpdateWorkspaceRating() {
        // 첫 번째 리뷰 등록 (4점)
        workspace.registerReviewStats(review1.getReviewRating());

        // 첫 번째 리뷰 반영된 평점 확인
        assertThat(workspace.getReviewCount()).isEqualTo(1);
        assertThat(workspace.getReviewRating()).isEqualTo(4.0);

        // 두 번째 리뷰 등록 (5점)
        workspace.registerReviewStats(review2.getReviewRating());

        // 두 번째 리뷰 반영 후 평점 재계산 확인
        // (4점 + 5점) / 2 = 4.5
        assertThat(workspace.getReviewCount()).isEqualTo(2);
        assertThat(workspace.getReviewRating()).isEqualTo(4.5); // 평균 평점 4.5
    }



    @Test
    @DisplayName("리뷰 업데이트 시 평점이 재계산된다")
    void updateReviewUpdatesWorkspaceRating() {
        // 리뷰 등록
        workspace.registerReviewStats(review1.getReviewRating());

        // 기존 리뷰 평점 확인
        assertThat(workspace.getReviewRating()).isEqualTo(4.0);

        // 리뷰 업데이트 (새로운 평점 5점으로 변경)
        Review updatedReview = Review.builder()
                .threadId(new BiddingThread.ID(THREAD_ID))
                .reviewRating(ReviewRating.from(5)) // 5점 리뷰
                .contentDetail("아주 만족스러웠습니다!")
                .contentGeneral(List.of(ContentGeneral.GOOD_SERVICE))
                .build();

        // 기존 리뷰를 5점으로 업데이트
        workspace.updateReviewStats(review1.getReviewRating(), updatedReview.getReviewRating());

        // 평점이 올바르게 재계산되었는지 확인
        assertThat(workspace.getReviewRating()).isEqualTo(5.0); // 5점으로 변경되어야 한다.
    }

    @Test
    @DisplayName("리뷰 삭제 시 평점이 재계산된다")
    void deleteReviewUpdatesWorkspaceRating() {
        // 리뷰 등록
        workspace.registerReviewStats(review1.getReviewRating());

        // 리뷰 삭제 (4점 리뷰 삭제)
        workspace.deleteReviewStats(review1.getReviewRating());

        // 평점이 0으로 돌아갔는지 확인
        assertThat(workspace.getReviewCount()).isEqualTo(0);
        assertThat(workspace.getReviewRating()).isEqualTo(0.0); // 리뷰 삭제 후 평점은 0이어야 한다.
    }


    @Test
    @DisplayName("리뷰 삭제 후 평점이 적절히 재계산된다")
    void deleteMultipleReviewsUpdatesWorkspaceRating() {
        // 첫 번째 리뷰 등록 (4점)
        workspace.registerReviewStats(review1.getReviewRating());
        // 두 번째 리뷰 등록 (5점)
        workspace.registerReviewStats(review2.getReviewRating());

        // 두 리뷰 반영된 평점 확인
        assertThat(workspace.getReviewCount()).isEqualTo(2);
        assertThat(workspace.getReviewRating()).isEqualTo(4.5);

        // 첫 번째 리뷰 삭제 (4점)
        workspace.deleteReviewStats(review1.getReviewRating());

        // 첫 번째 리뷰 삭제 후 평점 재계산
        // (5점) / 1 = 5.0
        assertThat(workspace.getReviewCount()).isEqualTo(1);
        assertThat(workspace.getReviewRating()).isEqualTo(5.0); // 나머지 리뷰는 5점

        // 두 번째 리뷰 삭제 (5점)
        workspace.deleteReviewStats(review2.getReviewRating());

        // 모든 리뷰 삭제 후 평점 0으로 리셋
        assertThat(workspace.getReviewCount()).isEqualTo(0);
        assertThat(workspace.getReviewRating()).isEqualTo(0.0); // 평점은 0이어야 한다.
    }



    @Test
    @DisplayName("유효하지 않은 리뷰 평점으로 예외가 발생한다")
    void invalidReviewRatingThrowsException() {
        // 유효하지 않은 평점 (0점 또는 6점 이상은 불가능)
        assertThatThrownBy(() -> ReviewRating.from(-1))
                .isInstanceOf(PeautyException.class)
                .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.INVALID_RATING);

        assertThatThrownBy(() -> ReviewRating.from(6))
                .isInstanceOf(PeautyException.class)
                .hasFieldOrPropertyWithValue("peautyResponseCode", PeautyResponseCode.INVALID_RATING);
    }

}