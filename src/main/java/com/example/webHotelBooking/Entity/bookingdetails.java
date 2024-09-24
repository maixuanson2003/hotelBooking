package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name="Bookingdetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class bookingdetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="CheckInDate")
    private String CheckInDate;
    @Column(name="CheckOutDate")
    private String CheckOutDate;
    @Column(name="numberPeople")
    private int numberPeople;
    @Column(name="price")
    private  long price;
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
    @JdbcTypeCode(SqlTypes.JSON)
    private booking booking;

}
