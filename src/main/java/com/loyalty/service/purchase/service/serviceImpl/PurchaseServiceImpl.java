package com.loyalty.service.purchase.service.serviceImpl;

import com.loyalty.service.purchase.client.CustomerClientService;
import com.loyalty.service.purchase.config.KafkaProducer;
import com.loyalty.service.purchase.entity.Purchase;
import com.loyalty.service.purchase.enums.ConversionRateEnum;
import com.loyalty.service.purchase.model.*;
import com.loyalty.service.purchase.repository.PurchaseRepository;
import com.loyalty.service.purchase.service.LoyaltyPointsService;
import com.loyalty.service.purchase.service.PurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private LoyaltyPointsService loyaltyPointsService;

    @Value(value = "${conversionRate:1}")
    private int conversionRate;

    @Value(value = "${rewardThreshold:100}")
    private int rewardThreshold;

    @Autowired
    private KafkaProducer walletProducer;

    @Autowired
    CustomerClientService customerClientService;

    @Override
    public PurchaseResponseDTO purchase(PurchaseRequest purchaseRequest) {

        log.debug("purchase service called ..");

        if (Objects.nonNull(purchaseRequest) && Objects.nonNull(purchaseRequest.customerID())) {

            log.debug("Calling customer client service to get customer data having customer id {} ", purchaseRequest.customerID());
            ResponseDTO purchaseDetails = customerClientService.getCustomerById(purchaseRequest.customerID());
            long loyaltyPoints = 0L;
            if (Objects.nonNull(purchaseDetails)) {
                if (purchaseRequest.redemptionPoints() != null && purchaseRequest.redemptionPoints() > 0) {
                    ConversionRateEnum conversionRateEnum = ConversionRateEnum.getByValue(conversionRate);
                    long pointsValue = Objects.requireNonNull(conversionRateEnum).getValue() * purchaseRequest.redemptionPoints();
                    if (pointsValue > purchaseRequest.purchaseAmount()) {
                        return PurchaseResponseDTO.builderSuper().resultMessage("Points Value more then purchase amount, currently 1 loyalty point = " + Objects.requireNonNull(conversionRateEnum).getValue() + "₹").resultCode(HttpStatus.OK.value()).build();
                    }
                    if (purchaseDetails.getResponse().walletBalance() < purchaseRequest.redemptionPoints()) {
                        return PurchaseResponseDTO.builderSuper().resultMessage("Insufficient balance").resultCode(HttpStatus.OK.value()).build();
                    }
                    loyaltyPoints = updateWallet(purchaseRequest.redemptionPoints(), purchaseRequest.purchaseAmount());
                } else {
                    loyaltyPoints = getLoyaltyPoint(purchaseRequest.purchaseAmount());
                }
                final var walletEvent = new WalletEvent("Wallet update status is in pending", "PENDING", new WalletDetails(purchaseDetails.getResponse().customerId(), loyaltyPoints));
                walletProducer.sendMessage(walletEvent);
                Purchase purchase = savePurchaseDetails(purchaseDetails, purchaseRequest);
                loyaltyPointsService.saveLoyaltyPointsDetails(purchase, purchaseRequest.purchaseAmount(),getLoyaltyPoint(purchase.getPurchaseAmount()));

                PurchaseResponse purchaseResponse = new PurchaseResponse(purchase.getCustomerId(), purchase.getPartnerStoreId(), purchase.getPurchaseAmount(), purchase.getPurchaseDate());

                return PurchaseResponseDTO.builderSuper().response(purchaseResponse).resultMessage("Purchase Successfully").resultCode(HttpStatus.OK.value()).build();
            }
        }
        return PurchaseResponseDTO.builderSuper().resultMessage("Customer not exist in our system").resultCode(HttpStatus.OK.value()).build();
    }

    private long updateWallet(Long redemptionPoints, Long purchaseAmount) {
        long points = 0L;
        if (purchaseAmount >= rewardThreshold) {
            Integer loyaltyPoint = getLoyaltyPoint(purchaseAmount);
            points = loyaltyPoint - redemptionPoints;
        }
        return points;
    }

    private Integer getLoyaltyPoint(Long purchaseAmount) {
        int amount = Math.round(purchaseAmount);
        int getPoint = amount / 100;
        return getPoint * 4;
    }

    public Purchase savePurchaseDetails(ResponseDTO purchaseDetails, PurchaseRequest purchaseRequest) {

        Purchase purchase = new Purchase();
        final var getCustomerDetails = new GetPointsResponse(purchaseDetails.getResponse().customerId(), purchaseDetails.getResponse().customerName(), purchaseDetails.getResponse().walletBalance());
        purchase.setCustomerId(getCustomerDetails.customerId());
        purchase.setPartnerStoreId(purchaseRequest.partnerStoreID());
        purchase.setPurchaseDate(LocalDateTime.now());
        ConversionRateEnum conversionRateEnum = ConversionRateEnum.getByValue(conversionRate);

        if (getCustomerDetails.walletBalance() == 0) {
            purchase.setPurchaseAmount(purchaseRequest.purchaseAmount());
        } else {
            Long points = Objects.requireNonNull(conversionRateEnum).getValue() * purchaseRequest.redemptionPoints();
            Long newPurchaseAmount = purchaseRequest.purchaseAmount() - points;
            purchase.setPurchaseAmount(newPurchaseAmount);
        }

        Purchase purchaseData = purchaseRepository.save(purchase);
        log.debug("Save purchase details {}", purchaseData);
        return purchaseData;
    }
}
