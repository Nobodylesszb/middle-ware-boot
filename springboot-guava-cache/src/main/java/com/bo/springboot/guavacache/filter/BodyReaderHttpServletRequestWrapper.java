package com.bo.springboot.guavacache.filter;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 处理request.getInputStream 只能读取一次的问题
 * @author bo
 * @version 1.0
 * @date 2020-09-24 17:16
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 用于将流保存下来
     */
    private byte[] requestBody;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        requestBody = StreamUtils.copyToByteArray(request.getInputStream());

    }

    @Override
    public ServletInputStream getInputStream() {

        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody);

        return new ServletInputStream() {

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
