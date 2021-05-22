package com.cloud.config;

import com.cloud.util.HttpClientUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置已经被修改后动态刷新SpringCloudConfig的配置
 */
public class RefreshCloudConfig {

    public static void main(String[] args) {
        Map<String,String> headers = new HashMap<>();
        headers.put("content-type","application/json");
        String res = HttpClientUtils.doPost("http://127.0.0.1:8811/actuator/bus-refresh",headers,null);
        System.out.println("Refresh finished,result:"+res);
    }
}
