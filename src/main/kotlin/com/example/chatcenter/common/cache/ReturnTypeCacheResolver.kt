package com.example.chatcenter.common.cache

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.interceptor.CacheOperationInvocationContext
import org.springframework.cache.interceptor.SimpleCacheResolver
import org.springframework.data.redis.cache.RedisCache
import java.util.*

class ReturnTypeCacheResolver(
    cacheManager: CacheManager
) : SimpleCacheResolver(cacheManager) {
    private fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return objectMapper
    }

    override fun resolveCaches(context: CacheOperationInvocationContext<*>): MutableCollection<out Cache> {
        val cacheNames = getCacheNames(context)

        val result = mutableListOf<Cache>()
        cacheNames.forEach { cacheName ->
            var cache: Cache? = cacheManager.getCache(cacheName)
            cache = cache?.let { wrappingRedisCache(context, it) }

            if (cache == null) {
                throw RuntimeException()
            }
            result.add(cache)
        }

        return result
    }

    private fun wrappingRedisCache(context: CacheOperationInvocationContext<*>, cache: Cache): Cache {
        if (cache is RedisCache) {
            return WrappingRedisCache(
                cache.name,
                cache.nativeCache,
                cache.cacheConfiguration,
                objectMapper(),
                context.method.returnType
            )
        }
        return cache
    }

}