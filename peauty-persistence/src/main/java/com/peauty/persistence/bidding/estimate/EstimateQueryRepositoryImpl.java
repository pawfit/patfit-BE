package com.peauty.persistence.bidding.estimate;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EstimateQueryRepositoryImpl implements EstimateQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;






}
