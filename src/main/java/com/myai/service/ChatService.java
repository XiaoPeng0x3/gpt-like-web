package com.myai.service;

import com.myai.dto.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public interface ChatService {
    Flux<String> sendMessage(Message message);
}
