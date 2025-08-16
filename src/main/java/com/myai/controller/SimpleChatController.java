package com.myai.controller;

import com.myai.dto.Message;
import com.myai.service.ChatService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class SimpleChatController {
    @Resource
    ChatService chatService;


    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sendStreamMessage(@RequestBody Message message) {
        return chatService.sendMessage(message);
    }
}

