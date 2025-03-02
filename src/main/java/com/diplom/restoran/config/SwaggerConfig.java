//package com.diplom.restoran.config;
//
//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.info.Info;
//import io.swagger.v3.oas.annotations.servers.Server;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@OpenAPIDefinition(
//        info = @Info(
//                title = "Ресторанный REST API",
//                version = "1.0",
//                description = "API для управления ресторанной системой: заказы, меню, персонал и столики."
//        ),
//        servers = {
//                @Server(url = "http://localhost:8080", description = "Локальный сервер")
//        }
//)
//public class SwaggerConfig {
//}
package com.diplom.restoran.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .info(new Info()
                                .title("Restaurant REST API")
                                .version("1.0")
                                .description("API для управления ресторанной системой: заказы, меню, персонал и столики."))
                        .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                        .components(new io.swagger.v3.oas.models.Components()
                                .addSecuritySchemes("BearerAuth",
                                        new SecurityScheme()
                                                .name("BearerAuth")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")));
        }
}