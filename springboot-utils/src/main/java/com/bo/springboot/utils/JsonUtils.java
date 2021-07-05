package com.bo.springboot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

public abstract class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 解析json为指定类型并使用Spring Validator进行参数校验
     *
     * @param json  json文本
     * @param clazz 目标类型
     * @throws BindException 参数校验失败
     */
    public static <T> T parseAndValidate(String json, Class<T> clazz) throws BindException {
        T o = parse(json, clazz);
        BindingResult errors = ValidationUtils.validate(o);
        if (errors.hasErrors()) {
            throw new BindException(errors);
        }
        return o;
    }

    /**
     * 解析json为指定类型
     *
     * @param json  json文本
     * @param clazz 目标类型
     */
    public static <T> T parse(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String stringify(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
