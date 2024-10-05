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
@Table(name="Hotel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nameHotel")
    private String nameHotel;
    @Column(name="Address")
    private String Address;
    @Column(name="Hotline")
    private String Hotline;
    @Column(name="Email")
    private String Email;
    @Column(name="Description")
    private String Description;
    @Column(name="TotalRoom")
    private Integer  TotalRoom;
    @Column(name="starPoint")
    private Integer  starPoint;
    @Column(name = "changefee")
    private Long changefee;
    @Column(name = "cancelfee")
    private Long cancelfee;
    @ManyToOne
    @JoinColumn(name = "cityId",nullable = false)
    private City City;
    @OneToMany(mappedBy = "Hotel",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<HotelImage> hotelImageList;
    @OneToMany(mappedBy = "Hotel",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<HotelRoom> hotelRoomList;
    @ManyToMany(mappedBy = "Hotel",cascade = CascadeType.ALL)
    private List<HotelFacility> hotelFacilityList;
    @OneToMany(mappedBy = "Hotel",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Review> reviewList;
    @ManyToMany(mappedBy = "hotelList")
    private List<HotelPolicy> hotelPolicyList;
    @OneToOne
    @JoinColumn(name = "AccountHotelId")
    private AccountHotel AccountHotel;
}
