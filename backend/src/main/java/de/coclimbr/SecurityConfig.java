package de.coclimbr;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .anyExchange().permitAll()
                .and().httpBasic()
                .and().build();
    }

    /*
      Redirect HTTP to HTTPS. This is a workaround because usage of two ports for http and https
      is not supported yet for webflux and netty
      see: https://github.com/spring-projects/spring-boot/issues/12035
      and: https://stackoverflow.com/questions/49045670/spring-webflux-redirect-http-to-https
    */

    @Bean
    public WebFilter httpsRedirectFilter() {
        return this::getWebFilter;
    }

    private Mono<Void> getWebFilter(ServerWebExchange exchange, WebFilterChain chain) {
        URI originalUri = exchange.getRequest().getURI();

        //here set your condition to http->https redirect
        List<String> forwardedValues = exchange.getRequest().getHeaders().get("x-forwarded-proto");
        if (forwardedValues != null && forwardedValues.contains("http")) {
            try {
                URI mutatedUri = new URI("https",
                        originalUri.getUserInfo(),
                        originalUri.getHost(),
                        originalUri.getPort(),
                        originalUri.getPath(),
                        originalUri.getQuery(),
                        originalUri.getFragment());
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
                response.getHeaders().setLocation(mutatedUri);
                return Mono.empty();
            } catch (URISyntaxException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
        return chain.filter(exchange);
    }
}

