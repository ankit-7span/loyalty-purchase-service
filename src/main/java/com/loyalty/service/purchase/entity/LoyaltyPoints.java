package com.loyalty.service.purchase.entity;

import com.loyalty.service.purchase.enums.ConversionRateEnum;
import jakarta.persistence.*;
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
public class LoyaltyPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long customerId;

    @OneToOne
    @JoinColumn(name = "purchase_id", referencedColumnName = "id")
    private Purchase purchase;

    private Long redemptionAmount;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private ConversionRateEnum conversionRate;

    @Override
    public String toString() {
        return "LoyaltyPoints{" + "Id=" + Id + ", customerId=" + customerId + ", purchase=" + purchase + ", redemptionAmount=" + redemptionAmount + ", date=" + date + ", conversionRateEnum=" + conversionRate.toString() + '}';
    }
}
