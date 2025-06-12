package com.challengeBP.accountMovements.infraestructure.output.adapter.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "services.client-account")
@Data
public class ClientAccountProps {
    private String baseUrl;
}
