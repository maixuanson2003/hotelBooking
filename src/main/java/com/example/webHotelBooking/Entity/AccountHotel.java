package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="AccountHotel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountHotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "phone")
    private String phone;
    @OneToOne
    @JoinColumn(name = "hotel_id",nullable = false)
    private Hotel hotel;
}
