package com.bo.elk.controller;


import com.bo.elk.annotation.ControllerWebLog;
import com.bo.elk.common.model.BaseRequest;
import com.bo.elk.common.model.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Web日志测试
 * @author ganchaoyang
 */
@RestController
@RequestMapping("/weblog")
@Api(tags = "Web日志测试相关接口")
@Slf4j
public class WebLogTestController {

    @GetMapping("/get-test")
    @ApiOperation("接口日志GET请求测试")
    @ControllerWebLog(name = "GET请求测试接口", intoDb = true)
    public String hello(@RequestParam("name") String name){
         log.info("this is test");
        return name;
    }

    @PostMapping("/post-test")
    @ApiOperation("接口日志POST请求测试")
    @ControllerWebLog(name = "接口日志POST请求测试", intoDb = true)
    public BaseResponse postTest(@RequestBody @Valid BaseRequest baseRequest, BindingResult bindingResult) {
        return BaseResponse.addResult();
    }

    @PostMapping("/test")
    @ApiOperation("接口日志TEST请求测试")
    @ControllerWebLog(name = "接口日志test请求测试", intoDb = true)
    public BaseResponse Test(@RequestBody @Valid BaseRequest baseRequest, BindingResult bindingResult) {
        return BaseResponse.addResult();
    }

}
