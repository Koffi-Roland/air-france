package com.afklm.spring.security.habile.demo;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions.Builder;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.afklm.spring.security.habile.demo.demo.DemoHandler;
import com.afklm.spring.security.habile.demo.hello.HelloHandler;
import com.afklm.spring.security.habile.demo.session.SessionHandler;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * Routing configuration of the demo app, experimenting with the webflux's functional routing
 * Also acts as entry point for protected handlers
 */
@Configuration
public class Router {
    /**
     * This function provides a bean implementation to route the requests on the /hello path
     *
     * @param handler binding the paths to some "business" actions
     * @return a configured router
     */
    @Bean
    public RouterFunction<ServerResponse> helloRoutes(HelloHandler handler) {
        Builder routes = route()
                .nest(path("/hello"), route()
                        .GET("", handler::hello)
                        .GET("/common", handler::helloCommon)
                        .GET("/user", handler::helloUser)
                        .GET("/admin", handler::helloAdmin)::build);

        return routes.build();
    }

    @Bean
    public RouterFunction<ServerResponse> userOnlyRoutes() {
        Builder routes = route()
                .GET("/redirect-me",
                        request -> ServerResponse.temporaryRedirect(URI.create("http://localhost:8080/me")).build())
                .nest(path("/api/user-only"), route()
                        .GET("/hello", request -> ServerResponse.ok().body(Mono.just("hello"), String.class))
                        .GET("/byebye", request -> ServerResponse.ok().body(Mono.just("say byebye get!"), String.class))
                        ::build);

        return routes.build();
    }

    /**
     * This function provides a bean implementation to route the requests on the /api path
     *
     * @param handler binding the paths to some "business" actions
     * @return a configured router
     */
    @Bean
    public RouterFunction<ServerResponse> apiRoutes(DemoHandler handler) {
        Builder routes = route()
                .nest(path("/api"), route()
                        .POST("/update", handler::update)
                        .GET("/user", handler::user)
                        .GET("/admin", handler::admin)::build);

        return routes.build();
    }
    
    
    /**
     * This function provides a bean implementation to route the requests on the /hello path
     *
     * @param handler binding the paths to some "business" actions
     * @return a configured router
     */
    @Bean
    public RouterFunction<ServerResponse> publicRoutes(SessionHandler handler) {
        Builder routes = route()
                .nest(path("/public"), route()
                        .GET("/data", handler::getData)
                        .PUT("/data/{what}", handler::setData)::build);
        return routes.build();
    }

}
