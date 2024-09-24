package com.example.webHotelBooking.Enums;

import lombok.Getter;

@Getter
public enum HotelStatus {
    ĐAĐAT(" ĐAĐAT"),SAPĐÊNHANTRA("SAPĐÊNHANTRA"),CHUADAT("CHUADAT");

    private String message;
    HotelStatus(String message) {
        this.message=message;
    }
}
