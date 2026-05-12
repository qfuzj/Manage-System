package com.ms.web.controller.system;

import com.ms.web.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/system/code")
public class RedisAutoCreateCodeController {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/getCode")
    public String getCode(){
        return redisUtil.generateOrderNo();
    }
}
