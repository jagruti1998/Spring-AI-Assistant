package com.ai.ai_backend.controller;

import com.ai.ai_backend.payload.CricketResponse;
import com.ai.ai_backend.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

@Controller
@RequestMapping("/api/v1/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public ResponseEntity<String>generateResponse(@RequestParam(value = "inputText") String inputText){

        String responseText=chatService.generateResponse(inputText);
        return  ResponseEntity.ok(responseText);

    }

    @GetMapping("/stream")
    public Flux<String> streamResponse(@RequestParam(value = "inputText") String inputText){

        Flux<String> response=chatService.streamResponse(inputText);
        return response;

    }

    @GetMapping("/cricketbot")
    public ResponseEntity<CricketResponse> getCricketResponse(
            @RequestParam ("inputText") String inputText
    ) throws JsonProcessingException {
        CricketResponse cricketResponse= chatService.generateCricketResponse(inputText);
        return ResponseEntity.ok(cricketResponse);
    }


}
