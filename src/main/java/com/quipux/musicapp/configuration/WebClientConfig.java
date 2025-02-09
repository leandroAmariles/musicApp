package com.quipux.musicapp.configuration;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import javax.net.ssl.SSLException;
import java.time.Duration;
import java.util.List;

@Configuration
@Slf4j
public class WebClientConfig {

    @Value("${webclient.connection.max.connections}")
    private int maxConnections;

    @Value("${webclient.connection.acquire.timeout}")
    private long acquireTimeOut;

    @Value("${webclient.connection.tls.version}")
    private List<String> tlsVersion;



    @Bean
    public WebClient insecureWebClient() throws SSLException {
        SslContext sslContext = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .protocols(tlsVersion)
                .build();

        HttpClient httpClient = HttpClient.create(
                ConnectionProvider.builder("insecure")
                        .maxConnections(maxConnections)
                        .pendingAcquireTimeout(Duration.ofSeconds(acquireTimeOut))
                        .build())
                .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
