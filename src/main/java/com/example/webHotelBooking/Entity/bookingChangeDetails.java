package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="bookingChangeDetailsRepository ")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class bookingChangeDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="createAt")
    private  String createAt;
    @Column(name = "CheckInDate")
    private LocalDate checkInDate;
    @Column(name = "CheckOutDate")
    private LocalDate checkOutDate;
    @Column(name = "Price")
    private Long Price;
    @Column(name = "status")
    private String status;
    @ManyToOne
    @JoinColumn(name="booking_Id",nullable = false)
    private booking booking;
}
