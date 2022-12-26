package com.pixelmoe.pixelimageservice.service;

import java.util.concurrent.TimeUnit;

public interface IRedisService {

    void set(String key, Object value);

    Object get(String key);

    void delete(String key);

    void deleteAll();

    boolean exists(String key);

    void setExpire(String key, long time, TimeUnit timeUnit);

    long getExpire(String key);

}
