package com.filmtrack;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 Clase de configuración global que habilita CORS en toda la aplicación.
 Permite que el frontend (por ejemplo, un proyecto HTML o JavaScript en otro puerto)
 pueda comunicarse con el backend sin bloqueos por política de orígenes cruzados.
 Conceptos de POO presentes:
 - Abstracción: encapsula la configuración del sistema en una clase independiente.
 - Encapsulamiento: centraliza la lógica de configuración, evitando repetirla en los controladores.
 - Responsabilidad única: su único propósito es definir la política de acceso CORS.
 */
@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
            }
        };
    }
}