package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="HotelFacility")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nameHotelFacility")
    private String nameHotelFacility;
    @OneToMany(mappedBy = "hotelFacility",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<HotelFacilityDetails> hotelFacilityDetailsList=new ArrayList<>();
    public  HotelFacility(String nameHotelFacility,String desCription){
        this.nameHotelFacility=nameHotelFacility;
    }

}
