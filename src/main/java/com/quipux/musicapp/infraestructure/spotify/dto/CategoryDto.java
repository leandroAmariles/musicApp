package com.quipux.musicapp.infraestructure.spotify.dto;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private String href;

    private String id;

    private List<Items> items;
}
