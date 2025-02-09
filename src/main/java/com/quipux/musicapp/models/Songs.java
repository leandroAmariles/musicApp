package com.quipux.musicapp.models;


import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Songs {

    private String title;

    private String artist;

    private String year;

    private String gender;
}

