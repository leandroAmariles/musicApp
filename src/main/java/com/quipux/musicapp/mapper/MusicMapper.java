package com.quipux.musicapp.mapper;

import com.quipux.musicapp.controller.out.ReproductionListRecord;
import com.quipux.musicapp.controller.out.SongDto;
import com.quipux.musicapp.models.ReproductionList;
import com.quipux.musicapp.models.Song;
import org.apache.kafka.common.protocol.types.Field;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface MusicMapper {


    @Mapping(source = "nombre", target = "name")
    @Mapping(source = "descripcion", target = "description")
    @Mapping(source = "canciones", target = "songsList")
    ReproductionList toReproductionList(ReproductionListRecord reproductionListRecord);


    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "description", target = "descripcion")
    @Mapping(source = "songsList", target = "canciones")
    @Mapping(source = "playListId", target = "uriReferencia", qualifiedByName = "mapUriReference")
    ReproductionListRecord toReproductionListRecord(ReproductionList reproductionList);

    @Mapping(source = "title", target = "titulo")
    @Mapping(source = "artist", target = "artista")
    @Mapping(source = "year", target = "anno")
    SongDto toSong(Song song);

    @Mapping(source = "titulo", target = "title")
    @Mapping(source = "artista", target = "artist")
    @Mapping(source = "anno", target = "year")
    Song toSongDto(SongDto songDto);


    @Named("mapUriReference")
    default URI mapUriReference(String playListId) {
        return URI.create("/music/".concat(playListId));

    }

    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "description", target = "descripcion")
    @Mapping(source = "songsList", target = "canciones", qualifiedByName = "mapSongList")
    ReproductionListRecord fromEntityToDto(com.quipux.musicapp.infraestructure.db.entity.ReproductionList reproductionListMono);


    @Named("mapSongList")
    default List<SongDto> mapSongList(List<com.quipux.musicapp.infraestructure.db.entity.Song> songsList) {
        return songsList.stream().map(song -> SongDto.builder()
                .titulo(song.getTitle())
                .artista(song.getArtist())
                .anno(song.getYear())
                .album(song.getGender())
                .build()).collect(Collectors.toList());
    }


}

