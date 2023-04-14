package com.loyalty.service.purchase.service;

import com.loyalty.service.purchase.entity.Purchase;

public interface LoyaltyPointsService {
    void saveLoyaltyPointsDetails(Purchase purchase, Long purchaseAmount);
}
