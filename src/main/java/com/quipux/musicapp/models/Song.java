package com.quipux.musicapp.models;


import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Song {

    private String title;

    private String artist;

    private String album;

    private String year;

    private String gender;
}

