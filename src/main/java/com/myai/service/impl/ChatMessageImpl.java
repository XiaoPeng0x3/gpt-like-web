package com.myai.service.impl;

import com.myai.dto.Message;
import com.myai.service.ChatService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
public class ChatMessageImpl implements ChatService {
    @Resource
    @Qualifier("ollamaNekoChatClient")
    private ChatClient chatClient;

    @Override
    public Flux<String> sendMessage(Message message) {
        String messageId = "msg_" + UUID.randomUUID(); // 生成唯一 messageId

        Flux<String> content = chatClient.prompt()
                .user(message.getMessage())
                .stream()
                .content();

        return Flux.concat(
                Flux.just(" {\"type\":\"start\",\"messageId\":\"" + messageId + "\"}"),
                content.map(chunk -> " {\"type\":\"content\",\"content\":\"" + escapeJson(chunk) + "\"}"),
                Flux.just(" {\"type\":\"end\",\"messageId\":\"" + messageId + "\"}")
        );
    }
    private String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}
