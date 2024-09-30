package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name="Booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="totalRoom")
    private int totalRoom;
    @JoinColumn(name = "User_Id",nullable = false)
    private actor actor;
    @Column(name="totalPrice")
    private long totalPrice;
    @Column(name="CreateAt")
    private String CreateAt;
    @Column(name="status")
    private String status;
    @OneToMany(mappedBy = "booking")
    private List<bookingdetails> bookingdetailsList;
    @OneToOne(mappedBy = "booking",cascade = CascadeType.ALL,orphanRemoval = true)
    private Payment payment;
}
