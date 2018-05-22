/**
*@Author: sam
*@Date: 2018年3月12日
*@Copyright: 2018  All rights reserved.
*/
package org.cloud.core.shiro.cache;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisShiroCache<K, V> implements Cache<K, V> {

	private static final long expire = 30;
	
	private String cacheKey;
	private RedisTemplate<String, V> redisTemplate;

	@SuppressWarnings("rawtypes")
	public RedisShiroCache(String name, RedisTemplate client) {
		this.cacheKey =name;
		this.redisTemplate = client;
	}

	@Override
	public V get(K key) throws CacheException {
		/***
		redisTemplate.boundValueOps(getCacheKey(key)).expire(SHIROCACHE_GLOBEXPIRE,
				TimeUnit.MINUTES);
		return redisTemplate.boundValueOps(getCacheKey(key)).get();
		***/
		return redisTemplate.opsForValue().get(cacheKey+key);
	}

	@Override
	public V put(K key, V value) throws CacheException {
		/***
		V old = get(key);
		redisTemplate.opsForValue().get(key)(getCacheKey(key)).set(value);
		return old;
		**/
        V previos = get(key);
        redisTemplate.opsForValue().set(cacheKey+key , value, expire, TimeUnit.MINUTES);
        return previos;
        
	}

	@Override
	public V remove(K key) throws CacheException {
		V old = get(key);
		redisTemplate.delete(cacheKey+key);
		return old;
	}

	@Override
	public void clear() throws CacheException {
		//redisTemplate.delete(keys());
		
		Set<String> redisKeys = redisTemplate.keys(cacheKey + "*");
        for (String redisKey : redisKeys) {
            redisTemplate.delete(redisKey);
        }
	}

	@Override
	public int size() {
		
		if (keys() == null)
	            return 0;
		return keys().size();
	}

    @SuppressWarnings("unchecked")
	@Override
	public Set<K> keys() {
		
		Set<String> redisKeys = redisTemplate.keys(cacheKey + "*");
        Set<K> keys = new HashSet<K>();
        for (String redisKey : redisKeys) {
            keys.add((K) redisKey.substring(cacheKey.length()));
        }
        return keys;
	}

	@Override
	public Collection<V> values() {
		
		  Set<String> redisKeys = redisTemplate.keys(cacheKey + "*");
	        Set<V> values = new HashSet<V>();
	        for (String redisKey : redisKeys) {
	            V value = redisTemplate.opsForValue().get(redisKey);
	            values.add(value);
	        }
	        return values;
	        
	}

}
