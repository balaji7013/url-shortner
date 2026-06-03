package com.mini.url.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis configuration for caching URL mappings.
 */
@Configuration
public class RedisConfig {

    /**
     * Configures RedisTemplate bean for String keys and Object values.
     * Uses StringRedisSerializer for keys and JSON serialization for values.
     * 
     * @param redisConnectionFactory Spring's Redis connection factory
     * @return Configured RedisTemplate
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        
        // Set connection factory
        template.setConnectionFactory(redisConnectionFactory);
        
        // String serializer for keys (readable in Redis CLI)
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        // Apply serializers for key-value operations
        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(stringSerializer);
        
        // Apply serializers for hash operations
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(stringSerializer);

        // Initialize template
        template.afterPropertiesSet();
        
        return template;
    }

    @Bean
    public org.springframework.cache.CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.create(connectionFactory);
    }
}
