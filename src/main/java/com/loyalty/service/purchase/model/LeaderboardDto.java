package com.loyalty.service.purchase.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LeaderboardDto extends BaseResponse{

    private List<LeaderboardData> response;

    @Builder(builderMethodName = "builderSuper")
    public LeaderboardDto(int resultCode, String resultMessage, List<LeaderboardData> response) {
        super(resultCode, resultMessage);
        this.response = response;
    }
}
