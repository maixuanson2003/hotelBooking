package com.example.webHotelBooking.Enums;

import lombok.Getter;

@Getter
public enum bookingChangeStatus {
    XACNHAN("XACNHAN"),CHUAXACNHAN("CHUAXACNHAN"),TUCHOI("TUCHOI");
    private String message;
    bookingChangeStatus(String message) {
        this.message=message;
    }
}
