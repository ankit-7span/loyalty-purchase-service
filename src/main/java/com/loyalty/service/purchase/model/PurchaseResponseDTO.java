package com.loyalty.service.purchase.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PurchaseResponseDTO extends BaseResponse {

    private PurchaseResponse response;

    @Builder(builderMethodName = "builderSuper")
    public PurchaseResponseDTO(int resultCode, String resultMessage, PurchaseResponse response) {
        super(resultCode, resultMessage);
        this.response = response;
    }
}
