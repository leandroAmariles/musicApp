package com.quipux.musicapp.controller.out;

import com.quipux.musicapp.models.Songs;

import java.net.URI;
import java.util.List;

public record ReproductionListRecord (

         String nombre,

         String descripcion,

         List<Songs> listaCanciones,

         URI uriReferencia
) {
}
