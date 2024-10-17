package com.example.webHotelBooking.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="Conversations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conversations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "LocalDate")
    private String LocalDate;
    @ManyToMany
    @JoinTable(
            name = "Coservation_actor",
            joinColumns = @JoinColumn(name = "Coservation_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<actor> actors;
    @OneToMany(mappedBy = "Conversation",cascade = CascadeType.REMOVE)
    private List<Chat> chats;


}
