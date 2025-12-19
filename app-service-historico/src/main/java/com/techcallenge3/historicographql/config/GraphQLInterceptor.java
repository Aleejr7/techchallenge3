package com.techcallenge3.historicographql.config;

import graphql.GraphQLContext;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GraphQLInterceptor implements WebGraphQlInterceptor {

    private final JwtUtil jwtUtil;

    public GraphQLInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        String token = request.getHeaders().getFirst("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            try {
                String role = jwtUtil.getRole(token);
                Long userId = jwtUtil.getUserId(token);

                // Adiciona no contexto GraphQL
                GraphQLContext graphQLContext = request.toExecutionInput().getGraphQLContext();
                graphQLContext.put("userRole", role);
                graphQLContext.put("userId", userId);
            } catch (Exception e) {
                // Token inválido ou expirado
            }
        }

        return chain.next(request);
    }
}

