package com.example.webHotelBooking.DTO.Response;

import com.example.webHotelBooking.Entity.Payment;
import lombok.*;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private String Url=null;
    private Date CreateAt=null;
    private boolean Check= false;
    private String transCode=null;
//    public  PaymentDTO(Payment payment){
//        u
//    }
}
