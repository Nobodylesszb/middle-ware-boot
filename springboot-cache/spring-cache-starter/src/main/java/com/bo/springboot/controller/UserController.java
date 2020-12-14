package com.bo.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.bo.springboot.entity.SysUser;
import com.bo.springboot.service.SysUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private SysUserService userService;

    @Value("${deepxi.iam.login.log.url}")
    private String loginLogUrl;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 查找所有
     *
     * @return
     */
    @RequestMapping("/getAll")
    public List<SysUser> getAll() {
        return userService.getAll();
    }

    /**
     * 根据id获取用户
     *
     * @return
     */
    @RequestMapping("/getOne")
    public SysUser getOne(Long id) {
        return userService.getOne(id);
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @PostMapping("/insertUser")
    public String insertUser(@RequestBody SysUser user) {
        userService.insertUser(user);
        return "insert success";
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    @RequestMapping("/updateUser")
    public String updateUser(SysUser user) {
        userService.updateUser(user);
        return "update success";
    }

    @GetMapping
    public String test() {

        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, String> params = new HashMap<>();
        params.put("contentFrom", null);
        params.put("tableName", null);
        params.put("type", "1");
        params.put("operation", "LOGOUT");
        params.put("menu", "退出");
        params.put("function", "退出登录");
        params.put("contentTo", null);
        JSONObject paramsObject = new JSONObject();
        paramsObject.put("contentFrom", null);
        paramsObject.put("tableName", null);
        paramsObject.put("type", 1);
        paramsObject.put("operation", "LOGOUT");
        paramsObject.put("menu", "退出");
        paramsObject.put("function", "退出登录");
        paramsObject.put("contentTo", null);

        String url = "http://localhost:9302/deepexi-staff-iam-openapi/api/v1.0/operatelog";
        ObjectMapper mapper = new ObjectMapper();
        String value = null;
        try {
            value = mapper.writeValueAsString(params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySW5mbyI6eyJhY2NvdW50SWQiOiIxIiwiZW50ZXJwcmlzZUNvZGUiOiJkZWVwZXhpIiwidGVuYW50SWQiOiJhNzVhN2E0MTZmNmM0YWY1OGI2Zjk5NmI3MjA1MjI2NSIsIm5lZWRSZXNldFBhc3N3b3JkIjoiZmFsc2UiLCJ1c2VySWQiOiIxIiwic2VjcmV0TGV2ZWwiOjIsInVzZXJuYW1lIjoiYWRtaW4ifSwiaWFtVHlwZSI6InN0YWZmIiwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJhbGwiXSwiZXhwIjoxNjA3OTQzNDI2LCJqdGkiOiJkZTUzNmJhYy1lNTdiLTQ5ZTEtOGJlNi02ZmIzMTgyODlhZjkiLCJjbGllbnRfaWQiOiJkZWVwZXhpIn0.F-gV_1y1gt1s1B95bs1NN73MQo0Rpadgetn2pFzTidU");
        HttpEntity<String> httpEntity = new HttpEntity<>(value, headers);
//        HttpEntity<HashMap<String, String>> httpEntity = new HttpEntity<HashMap<String, String>>(params, headers);
        try {
            ResponseEntity<String> payloadResponseEntity = this.restTemplate.postForEntity(loginLogUrl, httpEntity, String.class);
            String body = payloadResponseEntity.getBody();
            System.out.println(body);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "success";
    }

    @GetMapping("/test")
    public Boolean test2() {
        //构建参数
        JSONObject paramsObject = new JSONObject();
        paramsObject.put("contentFrom", null);
        paramsObject.put("tableName", null);
        paramsObject.put("type", 1);
        paramsObject.put("operation", "LOGOUT");
        paramsObject.put("menu", "退出");
        paramsObject.put("function", "退出登录");
        paramsObject.put("contentTo", null);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(loginLogUrl);// 创建httpPost
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySW5mbyI6eyJhY2NvdW50SWQiOiIxIiwiZW50ZXJwcmlzZUNvZGUiOiJkZWVwZXhpIiwidGVuYW50SWQiOiJhNzVhN2E0MTZmNmM0YWY1OGI2Zjk5NmI3MjA1MjI2NSIsIm5lZWRSZXNldFBhc3N3b3JkIjoiZmFsc2UiLCJ1c2VySWQiOiIxIiwic2VjcmV0TGV2ZWwiOjIsInVzZXJuYW1lIjoiYWRtaW4ifSwiaWFtVHlwZSI6InN0YWZmIiwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJhbGwiXSwiZXhwIjoxNjA3OTQyMTEyLCJqdGkiOiI2OWVlNTE2Ni1mMjAwLTQzZDYtOGQ1Ny0zYjMyNzU5NGQ3ZDUiLCJjbGllbnRfaWQiOiJkZWVwZXhpIn0.kok-K22rX6Mtp8h_HZLmdULtIrmPSHznbhywFc_vja8.eyJ1c2VySW5mbyI6eyJhY2NvdW50SWQiOiIxIiwiZW50ZXJwcmlzZUNvZGUiOiJkZWVwZXhpIiwidGVuYW50SWQiOiJhNzVhN2E0MTZmNmM0YWY1OGI2Zjk5NmI3MjA1MjI2NSIsIm5lZWRSZXNldFBhc3N3b3JkIjoiZmFsc2UiLCJ1c2VySWQiOiIxIiwic2VjcmV0TGV2ZWwiOjIsInVzZXJuYW1lIjoiYWRtaW4ifSwiaWFtVHlwZSI6InN0YWZmIiwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJhbGwiXSwiZXhwIjoxNjA3OTI0NjgyLCJqdGkiOiJlYjA5NzM4My1jMGYxLTQ3MDUtODA5MS02ODJmODdkNTYxZTgiLCJjbGllbnRfaWQiOiJkZWVwZXhpIn0.8kD9oV9aQHpuS85fnPNSiQDGe0fCG-LbNGPPtir4bS8");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(paramsObject.toJSONString(), charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;

        try {

            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.OK.value()) {
                return true;
            } else {
                log.error("请求返回:" + state + "(" + loginLogUrl + ")");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
}
