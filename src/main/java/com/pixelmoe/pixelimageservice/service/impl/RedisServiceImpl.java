package com.pixelmoe.pixelimageservice.service.impl;

import com.pixelmoe.pixelimageservice.service.IRedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements IRedisService {

    @Resource
    RedisTemplate<String, Object> redisImageRedisTemplate;

    @Override
    public void set(String key, Object value) {
        key = key.replace("/", ":");
        ValueOperations<String, Object> valueOperations = redisImageRedisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    @Override
    public Object get(String key) {
        key = key.replace("/", ":");
        ValueOperations<String, Object> valueOperations = redisImageRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    @Override
    public void delete(String key) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean exists(String key) {
        key = key.replace("/", ":");
        return Boolean.TRUE.equals(redisImageRedisTemplate.opsForValue().getOperations().hasKey(key));
    }

    @Override
    public void setExpire(String key, long time, TimeUnit timeUnit) {
        key = key.replace("/", ":");
        redisImageRedisTemplate.expire(key, time, timeUnit);
    }

    @Override
    public long getExpire(String key) {
        return 0;
    }
}
