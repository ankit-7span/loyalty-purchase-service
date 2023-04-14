package com.loyalty.service.purchase.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
public class BaseResponse {
    private int resultCode;
    private String resultMessage;

    public BaseResponse(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
}
