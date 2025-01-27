package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="HotelRoom")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "AmountRoom")
    private int AmountRoom;
    @Column(name = "typeRoom")
    private String typeRoom;
    @Column(name = "numberPeople")
    private Long numberPeople;
    @Column(name = "numberRoomLast")
    private int numbeRoomLast;
    @Column(name = "status")
    private String status;
    @Column(name = "pricePerNight")
    private Long pricePerNight;
    @Column(name = "image")
    private String image;
    @Column(name = "numberOfBooking")
    private int numberOfBooking;
    @ManyToOne
    @JoinColumn(name = "Hotel_Id", nullable = false)
    private Hotel hotel;
    @OneToMany(mappedBy = "hotelRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<bookingdetails> bookingdetails = new ArrayList<>();
    ;
    @ManyToMany(mappedBy = "HotelRoom")
    private List<HotelRoomFeatures> hotelRoomFeaturesList = new ArrayList<>();
    ;

    public HotelRoom(int AmountRoom, String typeRoom, Long numberPeople, String status, Long pricePerNight, long perNight, Hotel hotel) {
        this.AmountRoom = AmountRoom;
        pricePerNight = perNight;
        this.numberPeople = numberPeople;
        this.typeRoom = typeRoom;
        this.status = status;
        this.pricePerNight = pricePerNight;
        this.hotel = hotel;
    }
}
