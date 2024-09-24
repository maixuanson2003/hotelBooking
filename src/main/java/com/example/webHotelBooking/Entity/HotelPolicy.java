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
    @Column(name = "changefee")
    private Long changefee;
    @ManyToMany
    @JoinTable(
            name = " HotelPolicy_Hotel",
            joinColumns = @JoinColumn(name = "HotelPolicyId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "Hotel_Id", referencedColumnName = "id")
    )
    private List<Hotel> hotelList;
    public HotelPolicy(String namePolicy, String Description, String description, Long changefee){

        this.namePolicy=namePolicy;
        this.Description=Description;
        this.changefee=changefee;
    }
}
