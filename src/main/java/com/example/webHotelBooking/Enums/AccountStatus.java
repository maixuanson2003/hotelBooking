package com.example.webHotelBooking.Enums;

import lombok.Getter;

@Getter
public enum AccountStatus {
    ĐAĐANGKY("DAĐANGKY"),CHUAXACTHUC("CHUAXACTHUC");
    private String message;
    AccountStatus(String message) {
        this.message=message;
    }
}
