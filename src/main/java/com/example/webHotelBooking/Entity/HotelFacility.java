package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @Column(name = "description")
    private String desCription;
    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.REMOVE})
    @JoinTable(
            name = "HotelHotelFacility_Hotel",
            joinColumns = @JoinColumn(name = "HotelFacility_Id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "Hotel_Id", referencedColumnName = "id")
    )
    private List<Hotel> Hotel;
    public  HotelFacility(String nameHotelFacility,String desCription){
        this.nameHotelFacility=nameHotelFacility;
        this.desCription=desCription;
    }

}
