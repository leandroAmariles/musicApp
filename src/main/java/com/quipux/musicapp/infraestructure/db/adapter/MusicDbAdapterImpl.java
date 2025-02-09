package com.quipux.musicapp.infraestructure.db.adapter;


import com.quipux.musicapp.exception.PlayListNotFoundException;
import com.quipux.musicapp.infraestructure.db.MusicAdapter;

import com.quipux.musicapp.infraestructure.db.entity.ReproductionList;
import com.quipux.musicapp.infraestructure.db.repository.ReproductionListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;


import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class MusicDbAdapterImpl implements MusicAdapter {

    private final ReproductionListRepository reproductionListRepository;


    @Override
    public List<ReproductionList> getReproductionList() {
       return reproductionListRepository.findAll();
    }

    @Override
    public ReproductionList findReproductionListByName(String name) {
        return reproductionListRepository.findByName(name).orElseThrow(() ->
                new PlayListNotFoundException("Playlist not found"));
    }

    @Override
    public void deleteReproductionList(String name) {
        if (!existByName(name)){
            throw new PlayListNotFoundException("Playlist not found");
        }
        reproductionListRepository.deleteByName(name);
    }


    private boolean existByName(String name){
        return reproductionListRepository.existsByName(name);
    }
}
