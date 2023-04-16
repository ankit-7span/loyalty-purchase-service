package com.loyalty.service.purchase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@RedisHash("Leaderboard")
public class Leaderboard /*implements Serializable*/ {
    List<LeaderboardData> leaderboardData;
}
