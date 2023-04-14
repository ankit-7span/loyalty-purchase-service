package com.loyalty.service.purchase.enums;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public enum ConversionRateEnum {

    CONVERSION_RULE_ONE(1);

    private final Integer value;

    ConversionRateEnum(Integer value) {
        this.value = value;
    }

    public static ConversionRateEnum getByValue(Integer value) {
        for (ConversionRateEnum campaignStatus : values()) {
            if (campaignStatus.value.equals(value)) return campaignStatus;
        }
        return null;
    }
}
