package com.challengeBP.ClientAccount.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Accounts and Customers API",
                version = "1.0",
                description = "API para gestionar clientes y cuentas"
        )
)
@Configuration
public class OpenApiConfig {
}
