package com.bo.springboot.admin.module.sys.oauth2;

import com.bo.springboot.admin.common.exception.RRException;

import java.security.MessageDigest;
import java.util.Objects;
import java.util.UUID;

/**
 * @auther: bo
 * @Date: 2020/11/3 09:31
 * @version:
 * @description:
 */
public class TokenGenerator {

    private static final char [] hexCode = "0123456789abcdef".toCharArray();

    public static String generateValue(String param){
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new RRException("生成Token失败", e);
        }
    }

    private static String toHexString(byte[] data) {
        if(Objects.isNull(data)){
            return null;
        }
        StringBuilder r = new StringBuilder(data.length*2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    public static String generateValue(){
            return generateValue(UUID.randomUUID().toString());
    }

}
