package com.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiChatService {

    private final GeminiClientService geminiClientService;

    /* "content": { "parts": [ {"text": "It learns patterns from data to make decisions or predictions."}],"role": "model"},*/
    public String getResponse(String question){
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[]{
                                Map.of("text", question)
                        })
                }
        );

        return geminiClientService.fetchGeminiAnswer(requestBody);
    }
}
