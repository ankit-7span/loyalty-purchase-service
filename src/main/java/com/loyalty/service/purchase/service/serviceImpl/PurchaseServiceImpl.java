package com.loyalty.service.purchase.service.serviceImpl;

import com.loyalty.service.purchase.client.CustomerClientService;
import com.loyalty.service.purchase.client.WalletProducer;
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
    private Integer conversionRate;

    @Autowired
    private WalletProducer walletProducer;

    @Autowired
    CustomerClientService customerClientService;

    @Override
    public PurchaseResponseDTO purchase(PurchaseRequest purchaseRequest) {

        log.debug("purchase service called ..");

        if (Objects.nonNull(purchaseRequest) && Objects.nonNull(purchaseRequest.customerID())) {

            log.debug("Calling customer client service to get customer data having customer id {} ", purchaseRequest.customerID());
            ResponseDTO purchaseDetails = customerClientService.getCustomerById(purchaseRequest.customerID());

            if (Objects.nonNull(purchaseDetails)) {
                if (purchaseDetails.getResponse().walletBalance() < purchaseRequest.redemptionPoints()) {
                    return PurchaseResponseDTO.builderSuper().resultMessage("Insufficient balance").resultCode(HttpStatus.OK.value()).build();
                }
                final var walletDetails = new WalletDetails(purchaseDetails.getResponse().customerId(), purchaseRequest.redemptionPoints());
                final var walletEvent = new WalletEvent("Wallet update status is in pending", "PENDING", walletDetails);
                walletProducer.sendMessage(walletEvent);
                Purchase purchase = savePurchaseDetails(purchaseDetails, purchaseRequest);
                loyaltyPointsService.saveLoyaltyPointsDetails(purchase, purchaseRequest.purchaseAmount());

                PurchaseResponse purchaseResponse = new PurchaseResponse(purchase.getCustomerId(), purchase.getPartnerStoreId(), purchase.getPurchaseAmount(), purchase.getPurchaseDate());

                return PurchaseResponseDTO.builderSuper().response(purchaseResponse).resultMessage("Purchase Successfully").resultCode(HttpStatus.OK.value()).build();
            }
        }
        return PurchaseResponseDTO.builderSuper().resultMessage("Customer not exist in our system").resultCode(HttpStatus.OK.value()).build();
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
