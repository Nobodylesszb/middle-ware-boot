package com.bo.elk.controller;


import cn.hutool.json.JSONObject;
import com.bo.elk.annotation.ControllerWebLog;
import com.bo.elk.common.model.BaseRequest;
import com.bo.elk.common.model.BaseResponse;
import com.bo.elk.db.dto.SystemSettingDTO;
import com.bo.elk.db.num.SettingTargetType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

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

    @ApiOperation("增加日志配置")
    @PostMapping("/log1")
    public Long savelogConfigTest(@RequestBody JSONObject jsonObject ) {
        SystemSettingDTO systemSettingDTO = new SystemSettingDTO();
        systemSettingDTO.setTarget(SettingTargetType.get((String) jsonObject.get("target")));
        systemSettingDTO.setValue(jsonObject.get("value").toString());
        System.out.println(systemSettingDTO.toString());
        return 1L;
    }

    @ApiOperation("增加日志配置")
    @PostMapping("/log2")
    public Long savelogConfigTest2(@RequestBody Map<String,Object> jsonObject ) {
        SystemSettingDTO systemSettingDTO = new SystemSettingDTO();
        systemSettingDTO.setTarget(SettingTargetType.get(jsonObject.get("target").toString()));
        systemSettingDTO.setValue(jsonObject.get("value").toString());
        System.out.println(systemSettingDTO.toString());
        return 1L;
    }



}
