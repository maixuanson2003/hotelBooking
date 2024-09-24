package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name="OTP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "OTP")
    private String OTP;
    @Column(name = "useremail")
    private String useremail;
}
