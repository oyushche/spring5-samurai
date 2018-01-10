package com.luxoft.samurai;

import com.luxoft.SamuraiGenerator;
import com.luxoft.samurai.handlers.SamuraiController;
import com.luxoft.samurai.storage.SamuraiRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.doReturn;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(SamuraiController.class)
public class ControllerTests
{
    @Autowired
    private MockMvc mockMvc;

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

        doReturn(Mono.fromSupplier(()
                -> SamuraiGenerator.generateSamuraiWithIdAndName(id, name)))
                .when(this.repository).get(id);

        this.mockMvc.perform(get("/rest/samurai/" + id).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("{\"id\":" + id
                                + ",\"name\":\"" + name
                                + "\",\"age\":8,\"activity\":[{\"name\":\"Jump\"},{\"name\":\"Kill\"},{\"name\":\"Meditate\"}]}"));
    }

}
