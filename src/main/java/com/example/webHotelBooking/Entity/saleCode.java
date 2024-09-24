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
@Table(name="saleCode")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @OneToMany(mappedBy = "SaleCode",cascade = CascadeType.ALL,orphanRemoval = true)
    @JdbcTypeCode(SqlTypes.JSON)
    private List<bookingdetails>bookingdetailsList;
}
