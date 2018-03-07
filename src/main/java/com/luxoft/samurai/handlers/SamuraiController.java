package com.luxoft.samurai.handlers;

import com.luxoft.samurai.data.Samurai;
import com.luxoft.samurai.storage.SamuraiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/samurai")
public class SamuraiController
{
    @Autowired
    private SamuraiRepository repository;

    @GetMapping("/{id}")
    public Samurai byId(@PathVariable String id)
    {
        System.err.println("--> RestController # Get samurai by id handled, id: " + id);

        return this.repository.get(Long.parseLong(id)).block();
    }

    @GetMapping()
    public List<Samurai> all()
    {
        System.err.println("--> RestController # Returns list of Samurai objects");

        return this.repository.all().collectList().block();
    }
}
