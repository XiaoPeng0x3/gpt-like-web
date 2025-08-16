package com.myai.dto;

import com.myai.utils.MessageRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 *  这个Message包含一个用户发送的消息和AI生成的消息
 */
@Slf4j
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    /** 将来存储在数据库里面的id **/
    private int id;
    /** 该message 属于的对话id **/
    private String conversationId;
    /** 当前消息的类型，用户消息或者AI消息 **/
    private MessageRole role;
    /** 当前消息的内容 **/
    private String message;
    /** 当前消息的消息戳 **/
    private LocalDateTime timeStamp;
    /** 当前消息所使用的AI模型 **/
    private String model;

}
