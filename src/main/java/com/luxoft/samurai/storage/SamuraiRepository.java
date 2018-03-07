package com.luxoft.samurai.storage;

import com.luxoft.samurai.data.Activity;
import com.luxoft.samurai.data.Samurai;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SamuraiRepository
{
    Mono<Samurai> get(long id);

    Flux<Samurai> all();

    Mono<Void> save(Mono<Samurai> samurai);

    Flux<Activity> getAllActivities(long id);

    Mono<Void> addActivity(long id, Mono<Activity> activity);

    Mono<Void> removeActivity(long samuraiId, String activity);
}
