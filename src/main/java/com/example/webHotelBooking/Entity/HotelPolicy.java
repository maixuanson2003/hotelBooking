package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    @OneToMany(mappedBy = "hotelPolicy",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<HotelPolicyDetails> hotelPolicyDetailsList=new ArrayList<>();


}
