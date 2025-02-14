package com.diplom.restoran.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Ресторанный REST API",
                version = "1.0",
                description = "API для управления ресторанной системой: заказы, меню, персонал и столики."
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Локальный сервер")
        }
)
public class SwaggerConfig {
}