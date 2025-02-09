package com.quipux.musicapp.controller.out;



import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.net.URI;
import java.util.List;

@Builder
public record ReproductionListRecord (

        @NotNull(message = "El nombre no puede ser nulo")
         String nombre,

        @NotNull(message = "La descripcion no puede ser nula")
         String descripcion,

        @Valid
         List<SongDto> canciones,

         URI uriReferencia
) {
}
