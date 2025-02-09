package com.quipux.musicapp.service;

import com.quipux.musicapp.controller.out.ReproductionListRecord;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMusicService {


        public Mono<ReproductionListRecord> saveReproductionList(ReproductionListRecord reproductionListRecord);

        public Flux<ReproductionListRecord> listReproductionList();

        public Mono<ReproductionListRecord> getReproductionList(String name);

        public void deleteReproductionList(String id);
}
