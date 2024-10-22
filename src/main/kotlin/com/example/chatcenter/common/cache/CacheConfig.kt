package com.example.chatcenter.common.cache

import com.example.chatcenter.common.function.logger
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.CacheResolver
import org.springframework.cache.jcache.config.JCacheConfigurerSupport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.CacheKeyPrefix
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer


@EnableCaching
@Configuration
class CacheConfig(
    private val redisProperties: RedisProperties,
    private val objectMapper: ObjectMapper
) :
    JCacheConfigurerSupport() {

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            this.connectionFactory = redisConnectionFactory()
            this.keySerializer = StringRedisSerializer()
            this.valueSerializer = GenericJackson2JsonRedisSerializer(objectMapper)
        }
    }

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        logger().info("[REDIS] host : ${redisProperties.host}, port : ${redisProperties.port}")
        return LettuceConnectionFactory(redisProperties.host, redisProperties.port)
    }

    @Bean
    override fun cacheManager(): CacheManager {

        val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .disableCachingNullValues()
            .computePrefixWith(CacheKeyPrefix.simple())
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    GenericJackson2JsonRedisSerializer(objectMapper)
                )
            )

        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(redisConnectionFactory()).cacheDefaults(redisCacheConfiguration).build()
    }


    @Bean
    override fun cacheResolver(): CacheResolver {
        val cacheResolver: ReturnTypeCacheResolver = ReturnTypeCacheResolver(cacheManager())
        cacheResolver.cacheManager = cacheManager()
        return cacheResolver
    }

}