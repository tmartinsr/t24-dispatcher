package lu.raiffeisen.t24.dispatcher.filters;

import java.util.Arrays;
import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class T24DispatcherGatewayFilterFactory extends AbstractGatewayFilterFactory<T24DispatcherGatewayFilterFactory.Config> {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(T24DispatcherGatewayFilterFactory.class);

	public T24DispatcherGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		
        return (exchange, chain) -> {
        	
  //      	Mono<SecurityContext> mono = ReactiveSecurityContextHolder.getContext();
       	
        	return ReactiveSecurityContextHolder.getContext()
        			.map(SecurityContext::getAuthentication)
        			.map(Authentication::getPrincipal)
        			.cast(Jwt.class)
        			.onErrorStop()
        			.flatMap(jwt -> {
        				if (jwt != null)
        					log.debug("JWT Audience: " + jwt.getAudience());
        				
        				return chain.filter(exchange);
        			});
       	
//        	return chain.filter(exchange);
        	
        	
        	
        };
	}
	
    public static class Config {
    	
        private String authServerBaseUrl;
        private String tokenEndpointUrl;
        private String clientId;
        private String clientSecret;
        private String issuerContainerType;
        private String issuerContainerName;
        private String scope;
        private String audience;

        public String getAudience() {
			return audience;
		}

		public void setAudience(String audience) {
			this.audience = audience;
		}

		public String getScope() {
			return scope;
		}

		public void setScope(String scope) {
			this.scope = scope;
		}

		public String getAuthServerBaseUrl() {
            return authServerBaseUrl;
        }

        public void setAuthServerBaseUrl(String authServerBaseUrl) {
            this.authServerBaseUrl = authServerBaseUrl;
        }

        public String getTokenEndpointUrl() {
            return tokenEndpointUrl;
        }

        public void setTokenEndpointUrl(String tokenEndpointUrl) {
            this.tokenEndpointUrl = tokenEndpointUrl;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getIssuerContainerType() {
            return issuerContainerType;
        }

        public void setIssuerContainerType(String issuerContainerType) {
            this.issuerContainerType = issuerContainerType;
        }

        public String getIssuerContainerName() {
            return issuerContainerName;
        }

        public void setIssuerContainerName(String issuerContainerName) {
            this.issuerContainerName = issuerContainerName;
        }

    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("authServerBaseUrl", "tokenEndpointUrl", "clientId", "clientSecret", "issuerContainerType", "issuerContainerName", "audience", "scope");
    }

}
