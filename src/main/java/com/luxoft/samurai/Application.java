package com.luxoft.samurai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.luxoft.SamuraiGenerator;
import com.luxoft.samurai.handlers.SamuraiHandler;
import com.luxoft.samurai.storage.SamuraiRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.PathMatchConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
@Import(RouterConfig.class)

@ComponentScan
@SpringBootApplication
@EnableWebFlux
public class Application
{

    public static void main(String[] args)
    {

        SpringApplication app = new SpringApplication(Application.class);
        app.setWebApplicationType(WebApplicationType.REACTIVE);

        app.run(args);
    }

    @Bean
    SamuraiHandler getSamuraiHandler()
    {
        return new SamuraiHandler();
    }

    @Bean
    CommandLineRunner init(SamuraiRepository repository)
    {
        return (env) ->
        {
//			System.out.println("REPO --> " + repository);

            repository.save(Mono.fromSupplier(SamuraiGenerator::generateSamurai)).subscribe();
            repository.save(Mono.fromSupplier(SamuraiGenerator::generateSamurai)).subscribe();
//			repository.save(Mono.fromSupplier(SamuraiGenerator::generateSamurai)).subscribe();
        };
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MappingJackson2HttpMessageConverter converter =
                new MappingJackson2HttpMessageConverter(mapper);
        return converter;
    }


}
