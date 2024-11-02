package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="City")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="CodeCity",unique = true)
    private Long CodeCity;
    @Column(name="nameCity",unique = true)
    private String nameCity;
    @Column(name="image")
    private String image;
    @Column(name="Description")
    private String Descrription;
    @OneToMany(mappedBy ="City",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hotel> hotelList = new ArrayList<>();;
    @OneToMany(mappedBy ="City",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> eventList = new ArrayList<>();;
    public  City(Long id,Long CodeCity,String Descrription,String nameCity,String image){
        this.id=id;
        this.CodeCity=CodeCity;
        this.Descrription=Descrription;
        this.nameCity=nameCity;
        this.image=image;
    }
}
