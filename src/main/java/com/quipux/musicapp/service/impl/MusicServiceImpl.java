package com.quipux.musicapp.service.impl;

import com.quipux.musicapp.controller.out.ReproductionListRecord;
import com.quipux.musicapp.exception.GenderException;
import com.quipux.musicapp.exception.GeneralException;
import com.quipux.musicapp.infraestructure.db.MusicAdapter;
import com.quipux.musicapp.infraestructure.spotify.SpotifyAdapter;
import com.quipux.musicapp.mapper.MusicMapper;
import com.quipux.musicapp.models.ReproductionList;
import com.quipux.musicapp.service.IMusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class MusicServiceImpl implements IMusicService {

    @Value("${kafka.topic.saveReproductionList}")
    private String saveReproductionListTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final Map<String, CompletableFuture<ReproductionList>> pendingResponses = new ConcurrentHashMap<>();

    private final MusicMapper musicMapper = Mappers.getMapper(MusicMapper.class);

    private final SpotifyAdapter spotifyAdapter;

    private final MusicAdapter musicAdapter;



    @Override
    public Mono<ReproductionListRecord> saveReproductionList(ReproductionListRecord reproductionListRecord) {
        return checkCategoriesIsPermit(reproductionListRecord)
                .then(Mono.defer(() -> {
                    String correlationId = UUID.randomUUID().toString();
                    CompletableFuture<ReproductionList> future = new CompletableFuture<>();
                    pendingResponses.put(correlationId, future);
                    ReproductionList reproductionList = musicMapper.toReproductionList(reproductionListRecord);
                    reproductionList.setCorrelationId(correlationId);
                    kafkaTemplate.send(saveReproductionListTopic, correlationId, new Gson().toJson(reproductionList));
                    log.info("Sent Kafka message and awaiting response: {}", correlationId);
                    return Mono.fromFuture(future)
                            .timeout(Duration.ofSeconds(10))
                            .doOnError(error -> {
                                log.error("Timeout waiting for response for correlationId: {}", correlationId);
                                pendingResponses.remove(correlationId);
                            });
                }))
                .map(musicMapper::toReproductionListRecord)
                .onErrorResume(this::handleError);
    }

    @Override
    public Mono<ReproductionListRecord> getReproductionList(String name) {
        return Mono.just(musicAdapter.findReproductionListByName(name))
                .map(musicMapper::fromEntityToDto)
                .onErrorResume(this::handleError);
    }


    @Override
    public Flux<ReproductionListRecord> listReproductionList() {
        return Flux.defer(() -> {
            List<com.quipux.musicapp.infraestructure.db.entity.ReproductionList> list = musicAdapter.getReproductionList();
            return Flux.fromIterable(list)
                    .map(musicMapper::fromEntityToDto);
        });
    }

    @Override
    public void deleteReproductionList(String name) {
         musicAdapter.deleteReproductionList(name);
    }

    @KafkaListener(topics = "${kafka.topic.responseReproductionList}", groupId = "musicApp")
    public void listenForResponse(String message) {
        ReproductionList response = new Gson().fromJson(message, ReproductionList.class);
        String correlationId = response.getCorrelationId();

        CompletableFuture<ReproductionList> future = pendingResponses.remove(correlationId);
        if (future != null) {
            future.complete(response);
            log.info("Received response for correlationId: {}", correlationId);
        } else {
            log.warn("Received response for unknown correlationId: {}", correlationId);
        }
    }

    private Mono<Void> checkCategoriesIsPermit(ReproductionListRecord reproductionListRecord) {
        return spotifyAdapter.getCategoriesFromSpotify()
                .collectList()
                .flatMap(allowedCategories -> {
                    if (allowedCategories.isEmpty()) {
                        return Mono.error(new GenderException("Error retrieving categories from Spotify"));
                    }
                    for (var cancion : reproductionListRecord.canciones()) {
                        if (!allowedCategories.contains(cancion.genero())) {
                            return Mono.error(new GenderException("No se permiten canciones de la categoria " + cancion.genero(), allowedCategories));
                        }
                    }

                    return Mono.empty();
                })
                .doOnError(error -> log.error("Error checking permitted categories: {}", error.getMessage())).then();
    }


    private Mono<ReproductionListRecord> handleError(Throwable error) {
        if (error instanceof GenderException) {
            return Mono.error(error);
        } else if (error instanceof GeneralException) {
            return Mono.error(error);
        } else {
            return Mono.error(new GeneralException("Error saving reproduction list: " + error.getMessage()));
        }
    }



}
