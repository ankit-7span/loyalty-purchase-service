package com.loyalty.service.purchase.service.serviceImpl;

import com.loyalty.service.purchase.entity.LoyaltyPoints;
import com.loyalty.service.purchase.entity.Purchase;
import com.loyalty.service.purchase.enums.ConversionRateEnum;
import com.loyalty.service.purchase.repository.LoyaltyPointsRepository;
import com.loyalty.service.purchase.service.LoyaltyPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoyaltyPointsServiceImpl implements LoyaltyPointsService {

    @Autowired
    LoyaltyPointsRepository loyaltyPointsRepository;

    @Value(value = "${conversionRate:1}")
    private Integer conversionRate;

    @Override
    public void saveLoyaltyPointsDetails(Purchase purchase, Long purchaseAmount) {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints();
        loyaltyPoints.setCustomerId(purchase.getCustomerId());
        loyaltyPoints.setPurchase(purchase);
        loyaltyPoints.setRedemptionAmount(purchaseAmount - purchase.getPurchaseAmount());
        loyaltyPoints.setDate(purchase.getPurchaseDate());
        loyaltyPoints.setConversionRate(ConversionRateEnum.getByValue(conversionRate));
        loyaltyPointsRepository.save(loyaltyPoints);
    }
}
