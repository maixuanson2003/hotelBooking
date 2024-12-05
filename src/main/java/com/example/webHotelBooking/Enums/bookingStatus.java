package com.example.webHotelBooking.Enums;

import lombok.Getter;

@Getter
public enum bookingStatus {
    ĐA_THANH_TOAN("ĐÃ THANH TOÁN"),CHUA_THANH_TOAN("CHƯA THANH TOÁN"),HUY("HỦY"),HUYLICH("HỦYLỊCH"),THAYDOI("THAYĐỔI"),THANH_TOAN_MIEN_PHI("THANH TOAN MIEN PHI");
    private String message;
    bookingStatus(String message) {
        this.message=message;
    }
}
