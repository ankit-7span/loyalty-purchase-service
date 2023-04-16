package com.loyalty.service.purchase.repository;

import com.loyalty.service.purchase.entity.LoyaltyPoints;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoyaltyPointsRepository extends JpaRepository<LoyaltyPoints, Long> {

    @Query(value = "select sum(lp.loyalty_points) from loyalty_points lp where lp.customer_id = :customerId",nativeQuery = true)
    Long findByCustomerIdAndGroupByLoyaltyPoints(@Param("customerId") Long customerId);

    @Query(value = "select lp.customer_id, sum(lp.loyalty_points) as totalLoyaltyPoints from loyalty_points lp where date_part('month' ,lp.date) = :month  and date_part('year' ,lp.date) = :year  group by lp.customer_id order by totalLoyaltyPoints desc limit 3",nativeQuery = true)
    @CachePut(value="leaderboard", key = "#month + '+' + #year")
    List<Object[]> findTop3CustomerByMonthAndYear(@Param("month") int month, @Param("year") int year);
}
