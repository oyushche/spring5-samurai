package com.luxoft.webclient;

import com.luxoft.SamuraiGenerator;
import com.luxoft.samurai.data.Activity;
import com.luxoft.samurai.data.Samurai;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * -Dserver.port=8081
 */
@Log
@SpringBootApplication
public class SamuraiClient
{
    public static void main(String[] args)
    {
        SpringApplication.run(SamuraiClient.class, args);
    }

    @Bean
    WebClient webClient() {
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    CommandLineRunner demo(WebClient client)
    {
//        return getSamuraiList(client);

//        return addGeneratedSamurai(client);

        return addActivitySamuraiById(client, 1);
    }

    private CommandLineRunner addActivitySamuraiById(WebClient client, long samuraiId)
    {
        return strings -> client
                .get()
                .uri("/samurai/{id}", samuraiId)
                .retrieve()
                .bodyToMono(Samurai.class)
                .flatMap(samurai -> client
                        .post()
                        .uri("/samurai/{id}/activity", samuraiId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .syncBody(new Activity("hara-kiri"))
                        .retrieve()
                        .bodyToMono(Void.class)
                )
                .subscribe(System.err::println);
    }


    private CommandLineRunner getSamuraiList(WebClient client)
    {
        return strings -> client
                .get()
                .uri("/samurai")
                .retrieve()
                .bodyToFlux(Samurai.class)
                .subscribe(System.err::println);
    }

    private CommandLineRunner addGeneratedSamurai(WebClient client)
    {
        Samurai samurai = SamuraiGenerator.generateSamurai();

        System.err.println("--> Trying to add samurai: " + samurai);
        return strings -> client
                .post()
                .uri("/samurai")
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(samurai)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe(System.err::println);
    }


}
