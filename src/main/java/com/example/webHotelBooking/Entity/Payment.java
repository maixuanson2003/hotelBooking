package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name="Payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "booking_Id")
    @JdbcTypeCode(SqlTypes.JSON)
    private booking booking;
    @Column(name="createAt")
    private String createAt;
    @Column(name="TotalPrice")
    private long TotalPrice;
    @Column(name="status")
    private String status;
}
