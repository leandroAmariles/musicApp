package com.quipux.musicapp.infraestructure.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "PLAYLIST")
public class ReproductionList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playListId;

    private String name;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "reproduction_list_song",
            joinColumns = @JoinColumn(name = "playListId"),
            inverseJoinColumns = @JoinColumn(name = "songId")
    )
    private List<Song> songsList = new ArrayList<>();

    @Transient
    private String correlationId;
}
