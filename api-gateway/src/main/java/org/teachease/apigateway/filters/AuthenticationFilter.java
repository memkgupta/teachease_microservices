package org.teachease.apigateway.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
private  RouteValidator routeValidator;
@Autowired
RestTemplate restTemplate;
    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            ServerHttpRequest request = null;
if(routeValidator.isSecured.test(exchange.getRequest())) {
    if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
        throw new RuntimeException("missing authorization header");
    }
    String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        authHeader = authHeader.substring(7);
    }

    try {
//                    REST call to AUTH service
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+authHeader);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange( "http://localhost:8080/verify",
                            HttpMethod.GET,
                            entity,
                            Map.class);
        String userId = (String) response.getBody().get("userId");
       request= exchange.getRequest().mutate().header("X-USER-ID", userId).build();

    } catch (Exception e) {
        System.out.println("invalid access...!");
        throw new RuntimeException("un authorized access to application");
    }
}
            return chain.filter(exchange.mutate().request(request).build());
        }));
    }

    public static class Config {}
}
