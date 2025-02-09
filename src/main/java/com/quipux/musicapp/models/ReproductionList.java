package com.quipux.musicapp.models;


import lombok.*;

import java.util.List;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReproductionList {

    private String playListId;

    private String name;

    private String description;

    private List<Song> songsList;

    private String correlationId;

}
