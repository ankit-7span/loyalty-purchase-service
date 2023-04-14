package com.loyalty.service.purchase.model;

public record GetPointsResponse(Long customerId, String customerName, Long walletBalance) {
}