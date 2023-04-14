package com.loyalty.service.purchase.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResponseDTO extends BaseResponse {
    private GetPointsResponse response;

    @Builder(builderMethodName = "builderSuper")
    public ResponseDTO(int resultCode, String resultMessage, GetPointsResponse response) {
        super(resultCode, resultMessage);
        this.response = response;
    }
}
