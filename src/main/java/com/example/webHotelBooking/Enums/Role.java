package com.example.webHotelBooking.Enums;

import lombok.Getter;

@Getter
public enum Role {
    USER("USER"),ADMIN("ADMIN"),HOTELADMIN("HOTELADMIN");
    private String description;
    Role(String description) {
        this.description=description;
    }
}
