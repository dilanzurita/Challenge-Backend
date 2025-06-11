package com.challengeBP.accountMovements.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Account Movements API",
                version = "1.0",
                description = "API para gestionar clientes, cuentas y movimientos"
        )
)
@Configuration
public class OpenApiConfig {
}
