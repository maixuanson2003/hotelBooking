package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="HotelPolicy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "namePolicy")
    private String namePolicy;
    @Column(name = "Description")
    private String Description;
    @Column(name="isRelatedFee")
    private Boolean isRelatedFee;
    @Column(name="isFree")
    private Boolean isFree;
    @ManyToMany
    @JoinTable(
            name = " HotelPolicy_Hotel",
            joinColumns = @JoinColumn(name = "HotelPolicyId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "Hotel_Id", referencedColumnName = "id")
    )
    private List<Hotel> hotelList;
    public HotelPolicy(String namePolicy, String Description, String description, Boolean isRelatedFee,Boolean isFree){

        this.namePolicy=namePolicy;
        this.Description=Description;
        this.isRelatedFee=isRelatedFee;
        this.isFree=isFree;
    }
}
