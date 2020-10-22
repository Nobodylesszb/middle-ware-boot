package com.bo.rabbitmq.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Client {
    private String id = UUID.randomUUID().toString();
    private String name = "bo";
    private Integer age = 25;
    private LocalDateTime crateTime = LocalDateTime.now();
}
