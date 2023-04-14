package com.loyalty.service.purchase.repository;

import com.loyalty.service.purchase.entity.LoyaltyPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyaltyPointsRepository extends JpaRepository<LoyaltyPoints, Long> {
}
