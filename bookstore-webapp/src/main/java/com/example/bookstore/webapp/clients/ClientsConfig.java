package com.example.bookstore.webapp.clients;

import java.time.Duration;

import com.example.bookstore.webapp.ApplicationProperties;
import com.example.bookstore.webapp.clients.catelog.CatelogServiceClient;
import com.example.bookstore.webapp.clients.orders.OrderServiceClient;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
class ClientsConfig {
    private final ApplicationProperties properties;

    ClientsConfig(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Bean
    RestClientCustomizer restClientCustomizer() {
        var requestFactory = ClientHttpRequestFactoryBuilder.simple()
                .withCustomizer(c -> {
                    c.setConnectTimeout(Duration.ofSeconds(5));
                    c.setReadTimeout(Duration.ofSeconds(5));
                })
                .build();
        return restClientBuilder ->
                restClientBuilder.baseUrl(properties.apiGatewayUrl()).requestFactory(requestFactory);
    }

    @Bean
    CatelogServiceClient catelogServiceClient(RestClient.Builder builder) {
        RestClient restClient = builder.build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();
        return factory.createClient(CatelogServiceClient.class);
    }

    @Bean
    OrderServiceClient orderServiceClient(RestClient.Builder builder) {
        RestClient restClient = builder.build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();
        return factory.createClient(OrderServiceClient.class);
    }
}