package com.peauty.designer.business.bidding;

import com.peauty.domain.bidding.BiddingThread;

public interface BiddingThreadPort{
    BiddingThread getThreadById(Long threadId);
}
