package com.bo.springboot.utils;

/**
 * @author Y.H.Zhou - zhouyuhang@deepexi.com
 * @since 2021/1/18.
 * <p></p>
 */
public interface JsonParser<T> {
    /**
     * Json解析
     */
    T parse(String json);

    /**
     * 支持校验的Json解析
     */
    T parseAndValid(String json);
}
