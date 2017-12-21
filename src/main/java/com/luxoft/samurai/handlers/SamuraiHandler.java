package com.luxoft.samurai.handlers;

import com.luxoft.samurai.data.Activity;
import com.luxoft.samurai.data.Samurai;
import com.luxoft.samurai.storage.SamuraiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Component
public class SamuraiHandler
{
    @Autowired
    private SamuraiRepository repository;

    public Mono<ServerResponse> get(ServerRequest request)
    {
        long id = Long.parseLong(request.pathVariable("id"));

        System.err.println("--> Get samurai by id handled, id: " + id);

        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        Mono<Samurai> samuraiMono = this.repository.get(id);

        return samuraiMono
                .flatMap(this::packSamurai)
                .switchIfEmpty(notFound);
    }

    private Mono<ServerResponse> packSamurai(Samurai samurai)
    {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(samurai));
    }

    public Mono<ServerResponse> createSamurai(ServerRequest request)
    {
        System.err.println("--> Create samurai handled");
        Mono<Samurai> samuraiMono = request.bodyToMono(Samurai.class);

        return ServerResponse.ok()
                .build(this.repository.save(samuraiMono));
    }

    public Mono<ServerResponse> list(ServerRequest request)
    {
        System.err.println("--> Get all samurai handled");

        Flux<Samurai> samuraiFlux = this.repository.all();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(samuraiFlux, Samurai.class);
    }

    public Mono<ServerResponse> activityList(ServerRequest request)
    {
        long id = Long.parseLong(request.pathVariable("id"));

        System.err.println("--> Get all activities for samurai handled, id: " + id);

        Flux<Activity> activityFlux = this.repository.getAllActivities(id);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(activityFlux, Activity.class);
    }

    public Mono<ServerResponse> addActivity(ServerRequest request)
    {
        long id = Long.parseLong(request.pathVariable("id"));
        Mono<Activity> activityMono = request.bodyToMono(Activity.class);

        System.err.println("--> Add activity to samurai handled, id: " + id);

        Mono<Void> response = this.repository.addActivity(id, activityMono);

        return ServerResponse.ok().build(response);
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(activityFlux, Activity.class);
    }

}
