package com.quipux.musicapp.infraestructure.spotify;

import reactor.core.publisher.Flux;


public interface SpotifyAdapter {

    public Flux<String> getCategoriesFromSpotify();
}
