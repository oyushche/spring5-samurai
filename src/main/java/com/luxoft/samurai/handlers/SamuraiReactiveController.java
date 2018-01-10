package com.luxoft.samurai.handlers;

import com.luxoft.samurai.data.Samurai;
import com.luxoft.samurai.storage.SamuraiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive/samurai")
public class SamuraiReactiveController
{
    @Autowired
    private SamuraiRepository repository;

    @GetMapping("/{id}")
    public Mono<Samurai> byId(@PathVariable String id)
    {
        System.err.println("--> ReactiveController # Get samurai by id handled, id: " + id);

        return this.repository.get(Long.parseLong(id));
    }
}
