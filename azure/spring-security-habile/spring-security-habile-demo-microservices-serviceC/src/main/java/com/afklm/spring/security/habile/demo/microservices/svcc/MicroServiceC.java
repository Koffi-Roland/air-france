package com.afklm.spring.security.habile.demo.microservices.svcc;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import feign.codec.Decoder;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple3;

@SpringBootApplication
@RestController
@EnableFeignClients
@ImportAutoConfiguration({HttpMessageConvertersAutoConfiguration.class})
public class MicroServiceC {

    private final EurekaClient eurekaClient;

    private final ServiceA serviceA;

    private final ServiceB serviceB;

    public MicroServiceC(@Lazy EurekaClient eurekaClient, ServiceA serviceA, ServiceB serviceB) {
        this.eurekaClient = eurekaClient;
        this.serviceA = serviceA;
        this.serviceB = serviceB;
    }

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceC.class, args);
    }

    @RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<?> getResource(@RequestParam(name="client", required=false) String clientType) {

        final Mono<? extends Tuple3<ClientType,?,?>> tupleMono =
            switch (ClientType.fromName(clientType)) {
                case FEIGN         -> getResourceWithFeignClient();
                case WEBCLIENT     -> getResourceWithWebClient();
                case REST_TEMPLATE -> getResourceWithRestTemplate();
            };

        return tupleMono.map(tuple -> aggregateResources(tuple.getT1(), tuple.getT2(), tuple.getT3()));
    }

    private Mono<? extends Tuple3<ClientType,?,?>> getResourceWithFeignClient() {
        return Mono.zip(Mono.just(ClientType.FEIGN), getResourceWithFeignClient(serviceA), getResourceWithFeignClient(serviceB));
    }

    private Mono<?> getResourceWithFeignClient(MicroService service) {
        return Mono.fromCallable(() -> service.getResource()).subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<? extends Tuple3<ClientType,?,?>> getResourceWithWebClient() {
        return Mono.zip(Mono.just(ClientType.WEBCLIENT), getResourceWithWebClient(ServiceA.SERVICE_ID), getResourceWithWebClient(ServiceB.SERVICE_ID));
    }

    private Mono<?> getResourceWithWebClient(String serviceName) {
        return getResourceWithGenericClient(serviceName, url -> WebClient.create(url).get().retrieve().bodyToMono(Object.class));
    }

    private Mono<? extends Tuple3<ClientType,?,?>> getResourceWithRestTemplate() {
        return Mono.zip(Mono.just(ClientType.REST_TEMPLATE), getResourceWithRestTemplate(ServiceA.SERVICE_ID), getResourceWithRestTemplate(ServiceB.SERVICE_ID));
    }

    private Mono<?> getResourceWithRestTemplate(String serviceName) {
        return getResourceWithGenericClient(serviceName, url -> Mono
                .fromCallable(() -> new RestTemplate().getForObject(url, Object.class))
                .subscribeOn(Schedulers.boundedElastic()));
    }

    private Optional<String> getServiceUrl(String serviceName) {
        return Optional.ofNullable(eurekaClient)
                .map(client -> client.getNextServerFromEureka(serviceName, false))
                .map(InstanceInfo::getHomePageUrl);
    }

    private Mono<?> getResourceWithGenericClient(final String serviceName, final Function<String,Mono<?>> genericClient) {
        return getServiceUrl(serviceName)
                .map(genericClient::apply)
                .orElse(Mono.empty());
    }

    private static Map<String, Object> aggregateResources(ClientType clientType, Object resourceA, Object resourceB) {
        return Map.of("client", clientType.getName(), ServiceA.SERVICE_ID, resourceA, ServiceB.SERVICE_ID, resourceB);
    }

    public interface MicroService {
        @RequestMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE)
        Map<String, Object> getResource();
    }

    @FeignClient(ServiceA.SERVICE_ID)
    public interface ServiceA extends MicroService {
        String SERVICE_ID = "SERVICE-A";
    }

    @FeignClient(ServiceB.SERVICE_ID)
    public interface ServiceB extends MicroService {
        String SERVICE_ID = "SERVICE-B";
    }

    enum ClientType {
        REST_TEMPLATE("rest-template"),
        WEBCLIENT("webclient"),
        FEIGN("feign");

        private final String name;

        private static Map<String, ClientType> nameToClientTypeMap = Stream
                .of(ClientType.values())
                .collect(Collectors.toMap(ClientType::getName, Function.identity()));

        ClientType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static ClientType fromName(String name) {
            return Optional.ofNullable(name)
                    .map(nameToClientTypeMap::get)
                    .orElse(REST_TEMPLATE);
        }
    }

    // Workaround to get JSON decoder available with blocking Feign client in reactive environment
    // https://github.com/spring-cloud/spring-cloud-openfeign/issues/235#issuecomment-566899447
    @Configuration
    public static class FeignResponseDecoderConfig {
        @Bean
        public Decoder feignDecoder() {

            ObjectFactory<HttpMessageConverters> messageConverters = () -> {
                HttpMessageConverters converters = new HttpMessageConverters();
                return converters;
            };
            return new SpringDecoder(messageConverters);
        }
    }
}
