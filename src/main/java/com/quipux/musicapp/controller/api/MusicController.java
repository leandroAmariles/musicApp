package com.quipux.musicapp.controller.api;

import com.quipux.musicapp.controller.out.ReproductionListRecord;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface MusicController {


    public ResponseEntity<Mono<ReproductionListRecord>> saveReproductionList(ReproductionListRecord reproductionListRecord);

    public ResponseEntity<Flux<ReproductionListRecord>> listReproductionList();

    public ResponseEntity<Mono<ReproductionListRecord>> getReproductionList(String name);
    public ResponseEntity<Mono<Void>> deleteReproductionList(String id);
}
