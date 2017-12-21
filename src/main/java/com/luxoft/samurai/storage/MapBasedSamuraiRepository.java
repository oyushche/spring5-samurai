package com.luxoft.samurai.storage;

import com.luxoft.samurai.data.Activity;
import com.luxoft.samurai.data.Samurai;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MapBasedSamuraiRepository implements SamuraiRepository
{

    private Map<Long, Samurai> repository;

    public MapBasedSamuraiRepository()
    {
        repository = new HashMap<>(10);
    }

    @Override
    public Mono<Samurai> get(long id)
    {
        return Mono.justOrEmpty(repository.get(id));
    }

    @Override
    public Flux<Samurai> all()
    {
        return Flux.fromIterable(repository.values());
    }

    @Override
    public Mono<Void> addActivity(long id, Mono<Activity> activity)
    {
        return activity
                .doOnNext(a -> repository.get(id).addActivity(a))
                .then(Mono.empty());
    }

    @Override
    public Flux<Activity> getAllActivities(long id)
    {
        return Flux.fromIterable(repository.get(id).getActivity());
    }

    @Override
    public Mono<Void> save(Mono<Samurai> samurai)
    {
        return samurai.doOnNext(this::storeSamurai)
                .thenEmpty(Mono.empty());
    }

    private void storeSamurai(Samurai samurai)
    {
        samurai.setId(repository.size() + 1);
        repository.put(samurai.getId(), samurai);
    }

}
