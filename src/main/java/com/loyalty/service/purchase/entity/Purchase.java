package com.loyalty.service.purchase.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long customerId;

    private Long partnerStoreId;

    private Long purchaseAmount;

    private LocalDateTime purchaseDate;

    @Override
    public String toString() {
        return "Purchase{" + "Id=" + Id + ", customerId=" + customerId + ", partnerId=" + partnerStoreId + ", purchaseAmount=" + purchaseAmount + ", purchaseDate=" + purchaseDate + '}';
    }
}
