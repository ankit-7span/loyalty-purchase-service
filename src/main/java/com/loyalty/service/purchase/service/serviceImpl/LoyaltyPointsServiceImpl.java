package com.loyalty.service.purchase.service.serviceImpl;

import com.loyalty.service.purchase.entity.LoyaltyPoints;
import com.loyalty.service.purchase.entity.Purchase;
import com.loyalty.service.purchase.enums.ConversionRateEnum;
import com.loyalty.service.purchase.model.LeaderboardData;
import com.loyalty.service.purchase.model.LeaderboardDetailData;
import com.loyalty.service.purchase.model.LeaderboardDto;
import com.loyalty.service.purchase.model.LeaderboardRequest;
import com.loyalty.service.purchase.repository.LoyaltyPointsRepository;
import com.loyalty.service.purchase.service.LoyaltyPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoyaltyPointsServiceImpl implements LoyaltyPointsService {

    @Autowired
    LoyaltyPointsRepository loyaltyPointsRepository;

    public static final String HASH_KEY = "Leaderboard";

//    Autowired
//    private RedisTemplate template;

    @Value(value = "${conversionRate:1}")
    private Integer conversionRate;

    @Override
    public void saveLoyaltyPointsDetails(Purchase purchase, Long actualPurchaseAmount, Integer loyaltyPoint) {
        Long discountAmount = purchase.getPurchaseAmount();
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints();
        loyaltyPoints.setCustomerId(purchase.getCustomerId());
        loyaltyPoints.setPurchase(purchase);
        loyaltyPoints.setRedemptionAmount(actualPurchaseAmount - discountAmount);
        loyaltyPoints.setCreatedDate(purchase.getPurchaseDate());
        loyaltyPoints.setLoyaltyPoints(loyaltyPoint);
        loyaltyPoints.setConversionRate(ConversionRateEnum.getByValue(conversionRate));
        loyaltyPointsRepository.save(loyaltyPoints);
    }

    @Override
    public LeaderboardDto getLeaderboardData(LeaderboardRequest leaderboardRequest) {
        List<LeaderboardData> leaderboardData = new ArrayList<>();
        int year = Integer.parseInt(leaderboardRequest.getYearAndMonth().substring(0, 4));
        int month = Integer.parseInt(leaderboardRequest.getYearAndMonth().substring(4));
        if (!ObjectUtils.isEmpty(leaderboardRequest.getCustomerId())) {
            Long totalLoyaltyPoints = loyaltyPointsRepository.findByCustomerIdAndGroupByLoyaltyPoints(leaderboardRequest.getCustomerId(),month,year);
            leaderboardData.add(new LeaderboardData(leaderboardRequest.getCustomerId(), totalLoyaltyPoints));
        } else {
            List<LeaderboardDetailData> leaderboardDataObjects = loyaltyPointsRepository.findTop3CustomerByMonthAndYear(month, year);
            for (LeaderboardDetailData leaderboardDataObject : leaderboardDataObjects) {
                leaderboardData.add(new LeaderboardData(leaderboardDataObject.getCustomerId(),leaderboardDataObject.getTotalLoyaltyPoints()));
            }
        }
        return LeaderboardDto.builderSuper().response(leaderboardData).resultMessage("Leaderboard data fetch successfully!").resultCode(HttpStatus.OK.value()).build();
    }
}
