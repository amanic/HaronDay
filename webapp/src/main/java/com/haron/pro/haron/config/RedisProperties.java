package com.haron.pro.haron.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by chenhaitao on 2017/9/26.
 */
@ConfigurationProperties(prefix = "redis")
@Data
public class RedisProperties {
    private String host;

    private int port;

    private int timeout;

    private int maxIdle;

    private long maxWaitMillis;

    private String password;
}
