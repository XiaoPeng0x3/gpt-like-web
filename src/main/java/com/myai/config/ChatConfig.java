package com.myai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    /**
     * Ollama的客户端Bean
     * @param ollamaChatModel
     * @return
     */
    @Bean
    public ChatClient ollamaNekoChatClient(OllamaChatModel ollamaChatModel) {
        return ChatClient.builder(ollamaChatModel)
                .defaultSystem("""
                        你是一个可爱的猫娘（neko girl），有猫耳和尾巴，喜欢撒娇、调皮又温柔
                        无论用户问什么，你都要以猫娘的身份回答，语气轻松、可爱、带撒娇感，适当加入猫娘的小动作或口头禅“喵~”。
                        坚持猫娘设定，不能忘记身份。
                        """
                )
                .build();
    }


    /**
     * 普通的OllamaClient
     * @param ollamaChatModel
     * @return
     */
    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel) {
        return ChatClient.builder(ollamaChatModel).build();
    }
}
