package de.coclimbr;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CorsGlobalConfiguration implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry
                .addMapping("/data/*")
                .allowedOrigins("http://localhost:8080", "https://co-climbr.herokuapp.com")
                .allowedMethods("*");
    }
}
