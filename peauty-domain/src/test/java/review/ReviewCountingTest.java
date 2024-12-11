package review;

import com.peauty.domain.bidding.BiddingProcess;
import com.peauty.domain.bidding.BiddingThread;
import com.peauty.domain.customer.Customer;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.Workspace;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.puppy.Puppy;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.review.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("리뷰 카운팅 테스트")
class ReviewCountingTest {

    private static final Long REVIEW_ID = 1L;
    private static final Long CUSTOMER_ID = 1L;
    private static final Long PUPPY_ID_1 = 1L;
    private static final Long PUPPY_ID_2 = 2L;
    private static final Long DESIGNER_ID = 1L;
    private static final Long WORKSPACE_ID = 1L;
    private static final Long BIDDING_THREAD_ID_1 = 1L;
    private static final Long BIDDING_THREAD_ID_2 = 2L;

    private Review review;
    private Customer customer;
    private Designer designer;
    private Workspace workspace;
    private BiddingThread biddingThread;
    private BiddingProcess biddingProcess;

    private Review.ID reviewId1;
    private Review.ID reviewId2;
    private Customer customer1;

    @BeforeEach
    void setUp() {


    }
}