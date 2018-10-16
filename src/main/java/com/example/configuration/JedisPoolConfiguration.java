package com.example.configuration;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisPoolConfiguration {
    Logger logger = LoggerFactory.getLogger(JedisPoolConfiguration.class);

    @Bean("redisProperties")
    @Qualifier("redisProperties")
    @Primary
    @ConfigurationProperties(prefix="spring.redis")
    public RedisProperties redisProperties(){
        return new RedisProperties();
    }
    
//    @Bean("redisClusterConfiguration")
//    public RedisClusterConfiguration redisClusterConfiguration(@Qualifier("redisProperties") RedisProperties redisProperties){
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//        RedisNode redisNode = new RedisNode(redisProperties.getHost(), redisProperties.getPort());
//        Set<RedisNode> redisNodes = new HashSet<RedisNode>();
//        redisNodes.add(redisNode);
//        redisClusterConfiguration.setClusterNodes(redisNodes);
////        RedisPassword redisPassword = RedisPassword.of(redisProperties.getPassword());
////        redisClusterConfiguration.setPassword(redisPassword);
//        redisClusterConfiguration.setMaxRedirects(3);
//        return redisClusterConfiguration;
//    }

    @Bean("jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig(@Qualifier("redisProperties") RedisProperties redisProperties){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setMaxTotal(redisProperties.getJedis().getPool().getMaxActive());
        jedisPoolConfig.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getJedis().getPool().getMaxWait().toMillis());
        jedisPoolConfig.setMinIdle(redisProperties.getJedis().getPool().getMinIdle());
        return jedisPoolConfig;
    }
    
    @Bean("jedisPool")
    public JedisPool jedisPool(@Qualifier("redisProperties") RedisProperties redisProperties,
            @Qualifier("jedisPoolConfig") JedisPoolConfig jedisPoolConfig){
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisProperties.getHost(), redisProperties.getPort());
        return jedisPool;
    }
    
    @Bean("jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(@Qualifier("redisProperties") RedisProperties redisProperties){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
//      RedisPassword redisPassword = RedisPassword.of(redisProperties.getPassword());
//        redisStandaloneConfiguration.setPassword(redisPassword);
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        return connectionFactory;
    }
    
    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(@Qualifier("jedisConnectionFactory") RedisConnectionFactory jedisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }
    
    @Bean("cacheManager")
    public CacheManager cacheManager(@Qualifier("jedisConnectionFactory") RedisConnectionFactory jedisConnectionFactory){
        RedisCacheWriter rcw = RedisCacheWriter.nonLockingRedisCacheWriter(jedisConnectionFactory);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration.entryTtl(Duration.ofSeconds(30));
//        redisCacheConfiguration.serializeValuesWith(valueSerializationPair)
        CacheManager cacheManager = new RedisCacheManager(rcw, redisCacheConfiguration);
        return cacheManager;
    }
    
    
}
