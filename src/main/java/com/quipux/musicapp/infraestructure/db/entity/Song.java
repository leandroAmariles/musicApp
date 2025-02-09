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
@Entity(name = "song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;

    private String title;

    private String artist;

    @Column(name = "releaseYear")
    private String year;

    private String gender;

    @ManyToMany(mappedBy = "songsList", fetch = FetchType.EAGER)
    private List<ReproductionList> reroductionLists = new ArrayList<>();

}