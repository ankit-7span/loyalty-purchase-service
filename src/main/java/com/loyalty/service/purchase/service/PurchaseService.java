package com.loyalty.service.purchase.service;

import com.loyalty.service.purchase.model.PurchaseRequest;
import com.loyalty.service.purchase.model.PurchaseResponseDTO;

public interface PurchaseService {
    PurchaseResponseDTO purchase(PurchaseRequest purchaseRequest);
}