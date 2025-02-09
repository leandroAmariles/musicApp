package com.quipux.musicapp.infraestructure.spotify.adapter;

import com.google.gson.Gson;
import com.quipux.musicapp.infraestructure.spotify.SpotifyAdapter;
import com.quipux.musicapp.infraestructure.spotify.client.SpotifyClient;
import com.quipux.musicapp.infraestructure.spotify.dto.Items;
import com.quipux.musicapp.infraestructure.spotify.dto.SpotifyCategoriesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class SpotifyAdapterImpl implements SpotifyAdapter {

    private final SpotifyClient spotifyClient;

    @Override
    public Flux<String> getCategoriesFromSpotify() {
        return spotifyClient.getBearerSpotify()
                .flatMap(spotifyAuthResponse -> {
                    log.info("Spotify Auth Response: " + spotifyAuthResponse);
                    return spotifyClient.getCategories("Bearer " + spotifyAuthResponse.getAccessToken());
                })
                .map((categoryDto) -> {
                    SpotifyCategoriesResponse categoryDto1 = new Gson().fromJson(categoryDto, SpotifyCategoriesResponse.class);
                    log.info("CategoryDto: " + categoryDto1);
                    return categoryDto1;
                })
                .flatMapIterable(categoryDto -> {
                    List<String> categories = categoryDto.getCategories().getItems().stream().map(Items::getName).toList();
                    log.info("Categories: " + categories);
                    return categories;
                        }
                ).onErrorResume(e -> {
                    log.error("Error fetching categories from Spotify: " + e.getMessage());
                    return Flux.empty();
                });
    }

}
