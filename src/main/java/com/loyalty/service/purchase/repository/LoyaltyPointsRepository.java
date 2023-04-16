package com.loyalty.service.purchase.repository;

import com.loyalty.service.purchase.entity.LoyaltyPoints;
import com.loyalty.service.purchase.model.LeaderboardData;
import com.loyalty.service.purchase.model.LeaderboardDetailData;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoyaltyPointsRepository extends JpaRepository<LoyaltyPoints, Long> {

    @Query(value = "select sum(lp.loyalty_points) from loyalty_points lp where lp.customer_id = :customerId and date_part('month' ,lp.date) = :month and date_part('year' ,lp.date) = :year", nativeQuery = true)
    Long findByCustomerIdAndGroupByLoyaltyPoints(@Param("customerId") Long customerId, @Param("month") int month, @Param("year") int year);

    @Query(value = "select lp.customer_id as customerId, sum(lp.loyalty_points) as totalLoyaltyPoints from loyalty_points lp where date_part('month' ,lp.date) = :month  and date_part('year' ,lp.date) = :year  group by lp.customer_id order by totalLoyaltyPoints desc limit 3", nativeQuery = true)
    @Cacheable(value = "leaderboard", key = "#month + '+' + #year")
    List<LeaderboardDetailData> findTop3CustomerByMonthAndYear(@Param("month") int month, @Param("year") int year);
}
