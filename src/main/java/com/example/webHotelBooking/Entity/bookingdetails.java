package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="Bookingdetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class bookingdetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "CheckInDate")
    private LocalDate checkInDate;
    @Column(name = "CheckOutDate")
    private LocalDate checkOutDate;
    @Column(name="price")
    private  long price;
    @Column(name="amountRoom")
    private  int amountRoom;
    @Column(name="createAt")
    private  String createAt;
    @ManyToOne
    @JoinColumn(name = "saleCode_Id",nullable = false)
    private saleCode SaleCode;
    @ManyToOne
    @JoinColumn(name="HotelRoom_Id",nullable = false)
    private HotelRoom hotelRoom;
    @ManyToOne
    @JoinColumn(name="booking_Id",nullable = false)
    private booking booking;

}
