package com.example.webHotelBooking.Enums;

import lombok.Getter;

@Getter
public enum NamePolicy {
    CHINHSACHDOILICH("chính sách đổi lịch"),CHINHSACHHUYLICH("chính sách hủy lịch"),TRECON("trẻ con"),CHOMEO("chó mèo");
    private String message;
    NamePolicy(String message) {
        this.message=message;
    }
}
