package com.quipux.musicapp.infraestructure.db.repository;



import com.quipux.musicapp.infraestructure.db.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;



public interface SongRepository extends JpaRepository<Song, Long> {


}
