package com.loyalty.service.purchase.service.serviceImpl;

import com.loyalty.service.purchase.model.LeaderboardData;
import com.loyalty.service.purchase.model.LeaderboardDto;
import com.loyalty.service.purchase.model.LeaderboardRequest;
import com.loyalty.service.purchase.repository.LoyaltyPointsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoyaltyPointsServiceImplTest {

    @InjectMocks
    LoyaltyPointsServiceImpl loyaltyPointsService;

    @Mock
    LoyaltyPointsRepository loyaltyPointsRepository;

    @Test
    void getLeaderboardData() {
        final var leaderBoardData = new LeaderboardData(1L, 13L);
        List<LeaderboardData> leaderboardDataList = new ArrayList<>();
        leaderboardDataList.add(leaderBoardData);
        final var leaderboardRequest = new LeaderboardRequest("202304", 1L);
        int year = Integer.parseInt(leaderboardRequest.getYearAndMonth().substring(0, 4));
        int month = Integer.parseInt(leaderboardRequest.getYearAndMonth().substring(4));
        final var leaderboardDto = new LeaderboardDto(200, "Leaderboard data fetch successfully!", leaderboardDataList);
        when(loyaltyPointsRepository.findByCustomerIdAndGroupByLoyaltyPoints(leaderboardRequest.getCustomerId(), month, year)).thenReturn(13L);
        final var result = loyaltyPointsService.getLeaderboardData(leaderboardRequest);
        assertEquals(result.getResponse().get(0).totalLoyaltyPoints(), 13L);
    }

}