package com.example.webHotelBooking.DTO.Response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private String nameuser;
    private String Email;
    private String phoneNumber;
    private Long bookingId;
    private String Status;
    private Long TotalPrice;
    private Date timeCreate;
}
