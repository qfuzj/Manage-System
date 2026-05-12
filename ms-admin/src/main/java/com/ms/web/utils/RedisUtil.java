package com.ms.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate  redisTemplate;
    private static final String ORDER_KEY_PREFIX = "order:seq:"; // Redis Key 前缀
    private static final String BUSINESS_PREFIX = "ORD"; // 业务标识

    //订单编号自动生成(不重复)
    public String generateOrderNo() {
        // 获取当前日期（格式：yyyyMMdd）
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 生成 Redis key
        String redisKey = ORDER_KEY_PREFIX + date;

        // Redis 自增，获取当日订单编号
        Long increment = redisTemplate.opsForValue().increment(redisKey);

        // 设置过期时间（保证第二天重新开始编号）
        if (increment == 1) {
            redisTemplate.expire(redisKey, Duration.ofDays(1));
        }
        // 生成订单编号（格式：20250311ORD000001）
        return date + BUSINESS_PREFIX + String.format("%06d", increment);
    }

}