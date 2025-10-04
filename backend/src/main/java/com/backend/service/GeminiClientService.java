package com.backend.service;

import com.backend.settings.exceptions.GeminiAiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class GeminiClientService {
    @Value("${application.api.ai.gemini.key}")
    private String geminiApiKey;
    private final WebClient webClient;


    public GeminiClientService(
            WebClient.Builder webClientBuilder,
            @Value("${application.api.ai.gemini.url}") String url
    ){
        this.webClient = webClientBuilder
                .baseUrl(url)
                .build();
    }

    public String fetchGeminiAnswer(Map<String, Object> requestBody) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.queryParam("key", geminiApiKey).build())
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(
                        this::isErrorStatus,
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .map(body -> new GeminiAiException("Error while communicating with Gemini Ai : " + body,  clientResponse.statusCode()))
                )
                .bodyToMono(String.class)
                .block();
    }

    private boolean isErrorStatus(HttpStatusCode response) {
        return response.is4xxClientError() || response.is5xxServerError();
    }
}
