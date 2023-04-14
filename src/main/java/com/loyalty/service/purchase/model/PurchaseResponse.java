package com.loyalty.service.purchase.model;

import java.time.LocalDateTime;

public record PurchaseResponse(Long customerID, Long partnerStoreID, Long purchaseAmount, LocalDateTime purchaseDate) {
}
