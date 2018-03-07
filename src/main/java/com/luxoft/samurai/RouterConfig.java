package com.luxoft.samurai;

import com.luxoft.samurai.handlers.SamuraiHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig
{
    private static int counter = 1;

    @Bean
    public RouterFunction<ServerResponse> samuraiRouting(SamuraiHandler handler)
    {
        return RouterFunctions
                .route(GET("/samurai"), handler::list)
                .filter(filterEvery3rdRequest())
                .andRoute(GET("/samurai/{id}"), handler::get)
                .andRoute(POST("/samurai"), handler::createSamurai);
    }

    @Bean
    public RouterFunction<ServerResponse> samuraiActivityRouting(SamuraiHandler handler)
    {
        return RouterFunctions
                .route(GET("/samurai/{id}/activity"), handler::activityList)
                .andRoute(POST("/samurai/{id}/activity"), handler::addActivity);
    }

    private HandlerFilterFunction filterEvery3rdRequest()
    {
        return (request, next) ->
        {
            if (counter++ % 3 == 0)
            {
                System.out.println("This filter blocks every 3-rd request.");
                return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS).build();
            }

            return next.handle(request);
        };
    }

    @Bean
    public RouterFunction<ServerResponse> myError()
    {
        return RouterFunctions
                .route(GET("/error"),
                        request -> ServerResponse.ok().body(BodyInserters.fromObject("Test error page")));
    }



}
