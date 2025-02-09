package com.quipux.musicapp.infraestructure.spotify.client;


import com.quipux.musicapp.exception.GeneralException;
import com.quipux.musicapp.infraestructure.spotify.dto.CategoryDto;
import com.quipux.musicapp.infraestructure.spotify.dto.SpotifyAuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpotifyClient {

    private final WebClient webClient;

    @Value("${spotify.authorization.url}")
    private String spotifyUrlAuth;

    @Value("${spotify.client.id}")
    private String spotifyClientId;

    @Value("${spotify.client.secret}")
    private String spotifyClientSecret;

    @Value("${spotify.api.url}")
    private String spotifyUrl;

    public Mono<SpotifyAuthResponse> getBearerSpotify() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", spotifyClientId);
        formData.add("client_secret", spotifyClientSecret);
        return webClient.post()
                .uri(spotifyUrlAuth)
                .headers(httpHeaders -> {
                    httpHeaders.set(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");


                })
                .bodyValue(formData)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("Error response from Spotify: {}", errorBody);
                                    return Mono.error(new RuntimeException("Error response from Spotify: " + errorBody));
                                }))
                .bodyToMono(SpotifyAuthResponse.class);

    }

    public Mono<String> getCategories(String bearerToken) {
        return webClient.get().uri(spotifyUrl)
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("Error response from Spotify: {}", errorBody);
                                    return Mono.error(new GeneralException("Error response from Spotify: " + errorBody));
                                }))
                .bodyToMono(String.class)
                .doOnNext(response -> log.info("Response from Spotify: {}", response));
    }
}
