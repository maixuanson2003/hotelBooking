package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name="HotelRoomFeatures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelRoomFeatures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nameFeatures")
    private String nameFeatures;
    @ManyToMany
    @JoinTable(
            name = " HotelRoomFeatures_Room",
            joinColumns = @JoinColumn(name = "HotelRoomFeatures_Id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "HotelRoom_Id", referencedColumnName = "id")
    )
    private List<HotelRoom> HotelRoom;
}
