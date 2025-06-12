package com.challengeBP.accountMovements.infraestructure.output.adapter.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient clientAccountWebClient(ClientAccountProps props,
                                            WebClient.Builder builder) {
        return builder
                .baseUrl(props.getBaseUrl())
                .build();
    }
}
