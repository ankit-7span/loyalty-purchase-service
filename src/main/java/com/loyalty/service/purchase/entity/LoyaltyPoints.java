package com.loyalty.service.purchase.entity;

import com.loyalty.service.purchase.enums.ConversionRateEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class LoyaltyPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long customerId;

    @OneToOne
    @JoinColumn(name = "purchase_id", referencedColumnName = "id")
    private Purchase purchase;

    private Long redemptionAmount;

    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private ConversionRateEnum conversionRate;

    private Integer loyaltyPoints;
}
