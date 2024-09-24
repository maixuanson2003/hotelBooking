package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name="HotelImage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "Hotel_Id",nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Hotel Hotel;
    @Column(name="linkImage")
    private String LinkImage;
}
