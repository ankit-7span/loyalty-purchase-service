package com.loyalty.service.purchase.controller;

import com.loyalty.service.purchase.model.LeaderboardDto;
import com.loyalty.service.purchase.model.LeaderboardRequest;
import com.loyalty.service.purchase.service.LoyaltyPointsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/purchase/leaderboard")
public class LeaderboardController {

    @Autowired
    private LoyaltyPointsService loyaltyPointsService;

    @PostMapping("/")
    public LeaderboardDto getLeaderboardData(@Valid @RequestBody LeaderboardRequest leaderboardRequest) {
        return loyaltyPointsService.getLeaderboardData(leaderboardRequest);
    }
}
