package com.quipux.musicapp.controller.out;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

@Builder
public record SongDto(

         @NotNull(message = "El titulo no puede ser nulo")
         String titulo,

         @NotNull(message = "El artista no puede ser nulo")
         String artista,

         @NotNull(message = "El album no puede ser nulo")
         String album,

         @NotNull(message = "El año no puede ser nulo")
         String anno,

         @NotNull(message = "El genero no puede ser nulo")
         String genero

) {
}
