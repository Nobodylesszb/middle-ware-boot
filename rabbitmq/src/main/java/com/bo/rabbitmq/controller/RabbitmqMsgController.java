package com.bo.rabbitmq.controller;

import com.bo.rabbitmq.constants.DelayTypeEnum;
import com.bo.rabbitmq.mq.DelayMessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Date;
import java.io.IOException;

/**
 * @Auther: bo
 * @Date: 2020/4/11 10:51
 * @Version:
 * @Description:
 */

@Slf4j
@RequestMapping("rabbitmq")
@RestController
public class RabbitmqMsgController {

    @Autowired
    private DelayMessageSender sender;

    @RequestMapping("sendmsg")
    public void  sendMsg(String msg,Integer delayType){
        log.info("当前时间：{},收到请求，msg:{},delayType:{}", new Date(), msg, delayType);
        sender.sendMsg(msg, Objects.requireNonNull(DelayTypeEnum.getDelayTypeEnumByValue(delayType)));
    }

    @RequestMapping("delayMsg")
    public void delayMsg(String msg, Integer delayTime) {
        log.info("当前时间：{},收到请求，msg:{},delayTime:{}", new Date(), msg, delayTime);
        sender.sendMsg(msg, delayTime);
    }

    @RequestMapping("delayMsg2")
    public void delayMsg2(String msg, Integer delayTime) {
        log.info("当前时间：{},收到请求，msg:{},delayTime:{}", new Date(), msg, delayTime);
        sender.sendDelayMsg(msg, delayTime);
    }


}
