package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    @ManyToOne
    @JoinColumn(name = "cityId",nullable = false)
    private City City;
    @OneToMany(mappedBy = "Hotel",cascade = {CascadeType.ALL, CascadeType.REMOVE},orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<HotelImage> hotelImageList = new ArrayList<>();;
    @OneToMany(mappedBy = "hotel",cascade = {CascadeType.ALL, CascadeType.REMOVE},orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<HotelRoom> hotelRoomList = new ArrayList<>();;
    @ManyToMany(mappedBy = "Hotel",cascade = CascadeType.ALL)
    private List<HotelFacility> hotelFacilityList ;
    @OneToMany(mappedBy = "Hotel",cascade = {CascadeType.ALL, CascadeType.REMOVE},orphanRemoval = true)
    private List<Review> reviewList = new ArrayList<>();;
    @OneToMany (mappedBy = "hotel",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<HotelPolicyDetails> hotelPolicyDetailsList= new ArrayList<>();
    @OneToOne(mappedBy = "hotel",cascade = CascadeType.ALL,orphanRemoval = true)
    private AccountHotel AccountHotel;
}
