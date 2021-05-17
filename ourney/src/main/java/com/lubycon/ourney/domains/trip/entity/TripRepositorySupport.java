package com.lubycon.ourney.domains.trip.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class TripRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public TripRepositorySupport(JPAQueryFactory queryFactory) {
        super(Trip.class);
        this.queryFactory = queryFactory;
    }
}
