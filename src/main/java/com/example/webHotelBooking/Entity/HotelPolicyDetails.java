package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="HotelPolicyDetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelPolicyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numberPeople")
    private String description;
    @Column(name = "fee")
    private Long fee;
    @Column(name="coditionalInfo")
    private String coditionalInfo;
    @ManyToOne
    @JoinColumn(name = "hotelId",nullable = false)
    private Hotel hotel;
    @ManyToOne
    @JoinColumn(name = "HotelPolicyId",nullable = false)
    private HotelPolicy hotelPolicy;


}
