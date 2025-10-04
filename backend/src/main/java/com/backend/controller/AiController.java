package com.backend.controller;

import com.backend.service.AiChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {
    private final AiChatService aiChatService;

    @PostMapping("/qna")
    public ResponseEntity<?> askQuestion(@RequestBody Map<String, String> payload){
        String question = payload.get("question");
        String answer = aiChatService.getResponse(question);
        return ResponseEntity.ok(answer);
    }
}
