// En algún archivo de configuración, ej: com.mchscorp.integrajob.datapi.config.WebClientConfig.java

package com.mchscorp.integrajob.datapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${bne.api.base-url}")
    private String bneBaseUrl;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {

        // Define el nuevo tamaño del búfer (ej. 2MB)
        final int bufferSize = 2 * 1024 * 1024; // 2 MB

        // Configura las estrategias de intercambio
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(bufferSize))
                .build();

        return builder
                .baseUrl(bneBaseUrl)
                .exchangeStrategies(strategies)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}