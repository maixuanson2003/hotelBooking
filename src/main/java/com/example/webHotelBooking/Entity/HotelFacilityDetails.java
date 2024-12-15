package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="HotelFacilityDetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelFacilityDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description")
    private String desCription;
    @ManyToOne
    @JoinColumn(name = "hotelId",nullable = false)
    private Hotel hotel;
    @ManyToOne
    @JoinColumn(name = "HotelFacilityId",nullable = false)
    private HotelFacility hotelFacility;
}
