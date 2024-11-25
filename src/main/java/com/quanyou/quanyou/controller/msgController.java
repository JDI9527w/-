package com.quanyou.quanyou.controller;

import com.quanyou.quanyou.util.WechatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;


@RestController
@Slf4j
public class msgController {


    @Value("${mydata.appToken}")
    private String token;

    @GetMapping("/wechat/checkMsg")
    public String checkMsgSource(@RequestParam("signature") String signature,
                               @RequestParam("timestamp") String timestamp,
                               @RequestParam("nonce") String nonce,
                               @RequestParam("echostr") String echostr) {
        String sha1Str = WechatUtil.getSHA1(token, timestamp, nonce);
        if (StringUtils.hasText(sha1Str) && sha1Str.equals(signature)){
            return echostr;
        }
        return null;
    }
    @PostMapping("/wechat/checkMsg")
    public void receiveMsgSource(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException {
        log.info("************wechat请求:{}",req.getMethod());
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        try {
            Map<String, String> requsetMap = WechatUtil.xmlToMap(req);
            log.info("mapmsg:{}",requsetMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
