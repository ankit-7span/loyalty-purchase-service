package com.loyalty.service.purchase.model;

public record PurchaseRequest(Long customerID, Long partnerStoreID, Long purchaseAmount, Long redemptionPoints) {
}