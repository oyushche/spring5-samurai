package com.luxoft.samurai;

import com.luxoft.SamuraiGenerator;
import com.luxoft.samurai.data.Samurai;
import com.luxoft.samurai.handlers.SamuraiHandler;
import com.luxoft.samurai.storage.SamuraiRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@WebFluxTest(SamuraiHandler.class)
public class WebFluxTests
{
    @Autowired
    private WebTestClient webClient;

    @SpyBean
    private SamuraiRepository repository;

    @Before
    public void setup()
    {
        doReturn(Mono.empty()).when(this.repository).save(any());
    }

    @Test
    public void getSamurai() throws Exception
    {
        final long id = 2;
        final String name = "Udzuki";

        Samurai actualSamurai = SamuraiGenerator.generateSamuraiWithIdAndName(id, name);

        doReturn(Mono.fromSupplier(()
                -> actualSamurai))
                .when(this.repository).get(id);

        this.webClient.get()
                .uri("/samurai/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Samurai.class).isEqualTo(actualSamurai);
    }

}
