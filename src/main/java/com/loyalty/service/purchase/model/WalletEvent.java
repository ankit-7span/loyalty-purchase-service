package com.loyalty.service.purchase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record WalletEvent(String message, String status, WalletDetails walletDetails) {
}