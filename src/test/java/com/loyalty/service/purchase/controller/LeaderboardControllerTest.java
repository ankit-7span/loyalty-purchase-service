package com.loyalty.service.purchase.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.loyalty.service.purchase.model.LeaderboardData;
import com.loyalty.service.purchase.model.LeaderboardDto;
import com.loyalty.service.purchase.model.LeaderboardRequest;
import com.loyalty.service.purchase.service.LoyaltyPointsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LeaderboardController.class)
class LeaderboardControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    LoyaltyPointsService loyaltyPointsService;

    @Test
    void getLeaderboardData() throws Exception {
        final var leaderBoardData = new LeaderboardData(1L, 13L);
        List<LeaderboardData> leaderboardDataList = new ArrayList<>();
        leaderboardDataList.add(leaderBoardData);
        when(loyaltyPointsService.getLeaderboardData(any(LeaderboardRequest.class))).thenReturn(new LeaderboardDto(200, "Leaderboard data fetch successfully!", leaderboardDataList));
        final var request = post("/api/purchase/leaderboard").
                contentType(MediaType.APPLICATION_JSON).content("{\"customerId\":1,\"yearAndMonth\":\"202304\"}");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                       "resultCode": 200,
                                       "resultMessage": "Leaderboard data fetch successfully!",
                                       "response": [
                                           {
                                               "customerId":1,
                                               "totalLoyaltyPoints":13
                                           }
                                       ]
                                   }
                                 """
                )).andReturn();
    }
}