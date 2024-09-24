package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @Column(name = "roomNumber")
    private String roomNumber;
    @Column(name = "typeRoom")
    private String typeRoom;
    @Column(name = "numberPeople")
    private Long numberPeople;
    @Column(name = "status")
    private String status;
    @Column(name = "pricePerNight")
    private Long pricePerNight;
    @Column(name = "floorNumber")
    private int floorNumber;
    @Column(name = "numberOfBooking")
    private int numberOfBooking;
    @ManyToOne
    @JoinColumn(name = "Hotel_Id",nullable = false)
    private Hotel Hotel;
    @ManyToMany(mappedBy = "HotelRoom")
    private List<HotelRoomFeatures> hotelRoomFeaturesList;
    public HotelRoom(String roomNumber, String typeRoom, String status, long pricePerNight, long perNight, int floorNumber, Hotel hotel){
         this.roomNumber=roomNumber;
         pricePerNight=perNight;
         this.floorNumber=floorNumber;
        this.numberPeople=numberPeople;
        this.typeRoom=typeRoom;
        this.status=status;
        this.pricePerNight=pricePerNight;
        this.Hotel=hotel;
    }
}
