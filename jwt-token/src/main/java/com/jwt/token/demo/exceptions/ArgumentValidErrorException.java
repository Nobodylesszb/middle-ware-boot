package com.jwt.token.demo.exceptions;

import com.jwt.token.demo.constants.BaseEnumType;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * 参数校验异常
 *
 * @author huangzh
 * @since 2020/5/27 20:06
 */
public class ArgumentValidErrorException extends RuntimeException implements Serializable {
    /**
     * 错误code
     */
    private String code;

    public ArgumentValidErrorException() {
    }

    public ArgumentValidErrorException(String msg) {
        super(msg);
        this.code = "400";
    }

    public ArgumentValidErrorException(String msg, String code) {
        super(msg);
        this.code = code;
    }

    public ArgumentValidErrorException(BaseEnumType baseEnumType) {
        super(baseEnumType.getMsg());
        this.code = baseEnumType.getCode();
    }

    public ArgumentValidErrorException(String msg, Object... arguments) {
        super(MessageFormat.format(msg, arguments));
        this.code = "400";
    }

    public ArgumentValidErrorException(BaseEnumType baseEnumType, Object... arguments) {
        super(MessageFormat.format(baseEnumType.getMsg(), arguments));
        this.code = baseEnumType.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
