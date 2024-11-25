package com.quanyou.quanyou.util;


import com.alibaba.fastjson2.JSONObject;
import com.quanyou.quanyou.entity.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TokenUtil {

    @Value("${mydata.appID}")
    private String APP_ID;
    @Value("${mydata.appSecret}")
    private String APP_SECRET;

    private final AccessToken accessToken = new AccessToken();

    // 定时任务,单位秒,token过期时间7200秒,过期前5分钟执行.
    @Scheduled(timeUnit = TimeUnit.SECONDS,fixedDelay = 6900)
    private void getToken() {
        String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                APP_ID,
                APP_SECRET);
        String result = HttpUtil.doGet(url);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String token = jsonObject.getString("access_token");
        long expiresIn = jsonObject.getLong("expires_in");
        accessToken.setToken(token);
        accessToken.setExpireTime(expiresIn);
    }

    /**
     * 获取AccessToken
     *
     * @return
     */
    public String getAccessToken() {
        if (accessToken.isExpired()) {
            getToken();
        }
        return accessToken.getToken();
    }
}