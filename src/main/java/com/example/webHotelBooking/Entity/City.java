package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name="Description")
    private String Descrription;
    @OneToMany(mappedBy ="City",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hotel> hotelList;
    @OneToMany(mappedBy ="City",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> eventList;
}
