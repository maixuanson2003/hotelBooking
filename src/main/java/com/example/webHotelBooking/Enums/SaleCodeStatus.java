package com.example.webHotelBooking.Enums;

import lombok.Getter;

@Getter
public enum SaleCodeStatus {
    HETHAN("HETHAN"),CONHAN("CONHAN");
    private String message;
    SaleCodeStatus(String message) {
        this.message=message;
    }
}
