package com.quipux.musicapp.infraestructure.db.adapter;


import com.quipux.musicapp.exception.PlayListNotFoundException;
import com.quipux.musicapp.infraestructure.db.MusicAdapter;

import com.quipux.musicapp.infraestructure.db.entity.ReproductionList;
import com.quipux.musicapp.infraestructure.db.repository.ReproductionListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class MusicDbAdapterImpl implements MusicAdapter {

    private final ReproductionListRepository reproductionListRepository;


    @Transactional
    @Override
    public List<ReproductionList> getReproductionList() {
       List<ReproductionList> reproductionLists = reproductionListRepository.findAll();
       log.info("Size response {}", reproductionLists.size());
       return reproductionLists;
    }

    @Transactional
    @Override
    public ReproductionList findReproductionListByName(String name) {
        return reproductionListRepository.findByName(name).orElseThrow(() ->
                new PlayListNotFoundException("Playlist not found"));
    }

    @Transactional
    @Override
    public void deleteReproductionList(String name) {
        if (!existByName(name)) {
            throw new PlayListNotFoundException("Playlist not found");
        }
         reproductionListRepository.deleteByName(name);
    }

    private Boolean existByName(String name) {
        return reproductionListRepository.existsByName(name);
    }
}
