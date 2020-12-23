package com.senla.bulletinboard.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfiguration extends CachingConfigurerSupport {

    @Value("${token.expiration}")
    private Integer expiration;

    @Override
    public CacheManager cacheManager() {
        return new CaffeineCacheManager();
    }

    @Bean("invalidatedTokenCacheManager")
    public CacheManager timeoutCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(expiration, TimeUnit.MILLISECONDS));
        return cacheManager;
    }
}
