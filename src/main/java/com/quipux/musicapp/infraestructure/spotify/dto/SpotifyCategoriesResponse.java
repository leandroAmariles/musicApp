package com.quipux.musicapp.infraestructure.spotify.dto;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpotifyCategoriesResponse {

    private CategoryDto categories;
}
