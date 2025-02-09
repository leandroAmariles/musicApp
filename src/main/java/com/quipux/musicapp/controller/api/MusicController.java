package com.quipux.musicapp.controller.api;

import com.quipux.musicapp.controller.out.ReproductionListRecord;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface MusicController {


    public ResponseEntity<Mono<ReproductionListRecord>> saveReproductionList(ReproductionListRecord reproductionListRecord);

    public ResponseEntity<Flux<ReproductionListRecord>> listReproductionList();

    public ResponseEntity<Mono<ReproductionListRecord>> getReproductionList(String name);

    public ResponseEntity<Void> deleteReproductionList(String id);


    public ResponseEntity<Mono<ReproductionListRecord>> unsecuredSaveReproductionList(ReproductionListRecord reproductionListRecord);

    public ResponseEntity<Flux<ReproductionListRecord>> unsecuredListReproductionList();

    public ResponseEntity<Mono<ReproductionListRecord>> unsecuredGetReproductionList(String name);

    public ResponseEntity<Void> unsecuredDeleteReproductionList(String id);
}
