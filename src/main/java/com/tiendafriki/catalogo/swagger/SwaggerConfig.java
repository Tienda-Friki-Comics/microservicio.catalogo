package com.tiendafriki.catalogo.swagger;

import io.swagger.v3.oas.models.OpenAPI;// Clase principal de OpenAPI que representa la documentación completa de la API
import io.swagger.v3.oas.models.info.Contact;// Permite definir información de contacto (nombre, correo, sitio web, etc.)
import io.swagger.v3.oas.models.info.Info;// Permite definir información general de la API (título, descripción, versión)
import org.springframework.context.annotation.Bean;// Permite registrar métodos como Beans administrados por Spring
import org.springframework.context.annotation.Configuration;// Indica que esta clase contiene configuraciones de Spring

@Configuration // Indica que esta clase contiene configuración de Spring
public class SwaggerConfig {
    @Bean // Registra el objeto OpenAPI como un Bean de Spring
    public OpenAPI peliculasOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        
                        // Nombre de la API que aparecerá en Swagger
                        .title("API de Catalogo de Tienda Friki")

                        // Descripción general del proyecto
                        .description("API REST para la gestión de productos del catalogo, categorias y editoriales")

                        // Versión de la API
                        .version("1.0")

                        // Información de contacto
                        .contact(new Contact()
                                .name("Tienda Friki")
                                .email("soporte@tiendafriki.com")
                        )
                );
    }
}
