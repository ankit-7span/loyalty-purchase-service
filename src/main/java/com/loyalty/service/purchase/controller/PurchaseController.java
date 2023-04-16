package com.loyalty.service.purchase.controller;

import com.loyalty.service.purchase.model.PurchaseRequest;
import com.loyalty.service.purchase.model.PurchaseResponseDTO;
import com.loyalty.service.purchase.service.PurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping("/")
    public PurchaseResponseDTO purchase(@RequestBody PurchaseRequest purchaseRequest) {
        return purchaseService.purchase(purchaseRequest);
    }
}
