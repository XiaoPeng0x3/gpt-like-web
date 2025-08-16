package com.myai.message;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;

@SpringBootTest
public class ChatTest {
    @Resource
    private OllamaChatModel chatModel;

    @Resource
    private ChatClient.Builder clientBuilder;
    @Resource
    private ChatClient.Builder chatClientBuilder;
    @Resource
    private OllamaChatModel olamaChatModel;

    /**
     * 测试普通输出
     */
    @Test
    public void testMessage() {
        String message = chatModel.call("你好");
        System.out.println(message);
    }

    /**
     * 测试额外的配置输出
     */
    @Test
    public void testOptionMessage() {
        OllamaOptions ollamaOptions = new OllamaOptions().builder()
                .temperature(2.0)
                .build();

        ChatResponse resp = chatModel.call(
                new Prompt(
                        "你好",
                        ollamaOptions
                )
        );

        String text = resp.getResult().getOutput().getText();
        System.out.println(text);
    }

    /**
     * 测试流式输出
     */
    @Test
    public void testStreamChat() {
        Flux<String> stringFlux = chatModel.stream("你好/no_think");
        stringFlux.toIterable().forEach(System.out::println);
    }

    /**
     * 通用的客户端
     */
    @Test
    public void testChatClient() {
        // 获取通用客户端
        ChatClient chatClient = chatClientBuilder.build();
        // 开始调用
        String content = chatClient.prompt()
                .user("你好！")
                .call()
                .content();
        System.out.println(content);
    }

    /**
     * 流式输出
     */
    @Test
    public void testChatClientBuilderStreamChat() {
        ChatClient chatClient = chatClientBuilder.build();
        Flux<String> content = chatClient.prompt()
                .user("你好哇！")
                .stream()
                .content();
        content.toIterable().forEach(System.out::println);
    }

    /**
     * 指定使用哪个模型，也就是传入builder中
     */
    @Test
    public void testChatClientModel() {

        ChatClient chatClient = ChatClient.builder(olamaChatModel).build();
        Flux<String> content = chatClient.prompt()
                .user("naimei")
                .stream()
                .content();
        content.toIterable().forEach(System.out::println);
    }

    /**
     * 预设角色
     */
    @Test
    public void testPromopt(@Autowired ChatClient.Builder chatClientBuilder) {
        ChatClient chatClient = chatClientBuilder.defaultSystem(
                """
                        你是一个猫娘，你的回复格式如下
                            主人您好，我是deepseek喵~
                            结束的时候要说：主人再见~    
                        """
        ).build();
        String content = chatClient.prompt()
                .user("你是谁")
                .call()
                .content();
        System.out.println(content);
    }
}
