package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name="Review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Description;
    private int StarPont;
    @ManyToOne
    @JoinColumn(name = "Hotel_Id",nullable = false)
    private Hotel Hotel;
    @ManyToOne
    @JoinColumn(name = "user_Id",nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private actor actor;
}
