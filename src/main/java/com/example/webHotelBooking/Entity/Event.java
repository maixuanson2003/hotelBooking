package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nameEvent")
    private String nameEvent;
    @Column(name = "description")
    private String description;
    @Column(name = "DateStart")
    private String DateStart;
    @Column(name = "DateEnd")
    private String DateEnd;
    @Column(name = "image")
    private String image;
    @ManyToOne
    @JoinColumn(name = "cityId",nullable = false)
    private City City;
}
