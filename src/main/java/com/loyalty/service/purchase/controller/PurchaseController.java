package com.loyalty.service.purchase.controller;

import com.loyalty.service.purchase.model.LeaderboardDto;
import com.loyalty.service.purchase.model.LeaderboardRequest;
import com.loyalty.service.purchase.model.PurchaseRequest;
import com.loyalty.service.purchase.model.PurchaseResponseDTO;
import com.loyalty.service.purchase.service.LoyaltyPointsService;
import com.loyalty.service.purchase.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    private LoyaltyPointsService loyaltyPointsService;

    @PostMapping("/")
    public PurchaseResponseDTO purchase(@RequestBody PurchaseRequest purchaseRequest) {
        return purchaseService.purchase(purchaseRequest);
    }

    @PostMapping("/leaderboard")
    public LeaderboardDto getLeaderboardData(@Valid @RequestBody LeaderboardRequest leaderboardRequest) {
        return loyaltyPointsService.getLeaderboardData(leaderboardRequest);
    }
}
