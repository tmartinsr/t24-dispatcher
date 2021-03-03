package lu.raiffeisen.t24.dispatcher;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {
	/*
	@Value("${bearer-token-exchange.endpoint-uri:#{null}}")
	String issuerUri;
*/	
	@Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
         http.authorizeExchange()
         	.anyExchange()
         	.authenticated()
         	.and().oauth2ResourceServer().jwt(withDefaults());
         return http.build();
    }
/*	
	@Bean
	public ReactiveJwtDecoder jwtDecoder() {
	    return new NimbusReactiveJwtDecoder(jwkSetUri);
	}
*/
}
