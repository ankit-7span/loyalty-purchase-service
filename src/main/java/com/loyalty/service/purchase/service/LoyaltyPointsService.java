package com.loyalty.service.purchase.service;

import com.loyalty.service.purchase.entity.Purchase;
import com.loyalty.service.purchase.model.LeaderboardDto;
import com.loyalty.service.purchase.model.LeaderboardRequest;

public interface LoyaltyPointsService {
    void saveLoyaltyPointsDetails(Purchase purchase, Long purchaseAmount, Integer loyaltyPoint);

    LeaderboardDto getLeaderboardData(LeaderboardRequest leaderboardRequest);
}
