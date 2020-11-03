package com.bo.springboot.admin.common.utils;

import java.util.HashMap;

/**
 * @auther: bo
 * @Date: 2020/11/3 11:03
 * @version:
 * @description:
 */
public class MapUtils extends  HashMap<String, Object> {
    @Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
