package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name="saleCode")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class saleCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code")
    private String Code;
    @Column(name = "DateStart")
    private String DateStart;
    @Column(name = "DateEnd")
    private String DateEnd;
    @Column(name = "Description")
    private String Description;
    @Column(name = "discountPercentage")
    private float discountPercentage;
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "SaleCode",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<bookingdetails> BookingdetailsList;
}
