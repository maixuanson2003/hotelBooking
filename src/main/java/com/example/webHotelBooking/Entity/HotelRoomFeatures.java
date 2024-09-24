package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name="HotelRoomFeatures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelRoomFeatures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nameFeatures")
    private String nameFeatures;
    @Column(name = "description")
    private String description;
    @ManyToMany
    @JoinTable(
            name = " HotelRoomFeatures_Room",
            joinColumns = @JoinColumn(name = "HotelRoomFeatures_Id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "HotelRoom_Id", referencedColumnName = "id")
    )
    @JdbcTypeCode(SqlTypes.JSON)
    private List<HotelRoom> HotelRoom;
}
