package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name="ReviewService")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Description;
    private int StarPont;
    private LocalDateTime CreatAt;
    private String status;
    @ManyToOne
    @JoinColumn(name = "Hotel_Id",nullable = false)
    private Hotel Hotel;
    @ManyToOne
    @JoinColumn(name = "user_Id",nullable = false)
    private actor actor;
}
