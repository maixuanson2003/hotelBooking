package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
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
    @OneToMany(mappedBy = "Hotel",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<HotelImage> hotelImageList = new ArrayList<>();;
    @OneToMany(mappedBy = "hotel",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<HotelRoom> hotelRoomList = new ArrayList<>();;
    @ManyToMany(mappedBy = "Hotel",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<HotelFacility> hotelFacilityList ;
    @OneToMany(mappedBy = "Hotel",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Review> reviewList = new ArrayList<>();;
    @ManyToMany(mappedBy = "hotelList")
    private List<HotelPolicy> hotelPolicyList= new ArrayList<>();;
    @OneToOne
    @JoinColumn(name = "AccountHotelId")
    private AccountHotel AccountHotel;
}
