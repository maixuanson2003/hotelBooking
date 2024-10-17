package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="totalRoom")
    private int totalRoom;
    @ManyToOne
    @JoinColumn(name = "User_Id",nullable = false)
    private actor actor;
    @Column(name="totalPrice")
    private long totalPrice;
    @Column(name="CreateAt")
    private String CreateAt;
    @Column(name="status")
    private String status;
    @Column(name="numberPeople")
    private int numberPeople;
    @OneToMany(mappedBy = "booking",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<bookingdetails> bookingdetailsList;
    @OneToMany(mappedBy = "booking",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<bookingChangeDetails> bookingChangeDetailsList;
    @OneToOne(mappedBy = "booking",cascade = CascadeType.ALL,orphanRemoval = true)
    private Payment payment;
}
