package com.peauty.customer.business.review.dto;

import com.peauty.domain.review.ContentGeneral;
import com.peauty.domain.review.ReviewImage;
import com.peauty.domain.review.ReviewRating;
import lombok.Builder;

import java.util.List;

@Builder
public record UpdateReviewCommand(
        ReviewRating reviewRating,
        String contentDetail,
        List<ContentGeneral> contentGenerals,
        List<ReviewImage> reviewImages
) {}