package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

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
    private booking booking;
    @CreationTimestamp
    @Column(name="createAt")
    private Date create_at;
    @Column(name="TotalPrice")
    private long TotalPrice;
    @Column(name="status")
    private String status;
    @ManyToOne
    @JoinColumn(name = "actorid")
    private actor actor;
}
