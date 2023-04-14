package com.loyalty.service.purchase.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public record WalletDetails(Long customerId,Long loyaltyPoints) {
}
