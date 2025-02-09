package com.quipux.musicapp.controller;

import com.quipux.musicapp.controller.out.ReproductionListRecord;
import com.quipux.musicapp.service.IMusicService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/music")
@RequiredArgsConstructor
public class MusicController implements com.quipux.musicapp.controller.api.MusicController {

    private final IMusicService iMusicService;


    @Override
    @PostMapping("/save")
    public ResponseEntity<Mono<ReproductionListRecord>> saveReproductionList(@Valid @RequestBody ReproductionListRecord reproductionListRecord) {
        return new ResponseEntity<>(iMusicService.saveReproductionList(reproductionListRecord), HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/list")
    public ResponseEntity<Flux<ReproductionListRecord>> listReproductionList() {
        return new ResponseEntity<>(iMusicService.listReproductionList(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/lists/{listName}")
    public ResponseEntity<Mono<ReproductionListRecord>> getReproductionList(@PathVariable String listName) {
        return new ResponseEntity<>(iMusicService.getReproductionList(listName), HttpStatus.OK);
    }


    @Override
    @DeleteMapping("/lists/{listName}")
    public ResponseEntity<Mono<Void>> deleteReproductionList(@PathVariable String listName) {
        return new ResponseEntity<>(iMusicService.deleteReproductionList(listName), HttpStatus.NO_CONTENT);
    }
}
