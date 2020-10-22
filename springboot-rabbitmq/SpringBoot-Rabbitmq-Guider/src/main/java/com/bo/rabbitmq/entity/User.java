package com.bo.rabbitmq.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class User implements Serializable {
    private String id = UUID.randomUUID().toString();
    private String name = "bo";
    private Integer age = 5;
    private LocalDateTime crateTime = LocalDateTime.now();
}