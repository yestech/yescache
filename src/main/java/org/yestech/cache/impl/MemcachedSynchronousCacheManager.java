package org.yestech.cache.impl;

import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.yestech.cache.ICacheManager;
import org.yestech.lib.util.Pair;

import java.util.Map;
import java.util.Collection;


/**
 * An implements of a synchronous cache using <a href="http://www.danga.com/memcached/">Memcached</a> using the
 * <a href="http://code.google.com/p/spymemcached">Spy</a> Client.
 *
 */
@SuppressWarnings("unchecked")
public class MemcachedSynchronousCacheManager<V> implements ICacheManager<String, V> {
    final private static Logger logger = LoggerFactory.getLogger(MemcachedSynchronousCacheManager.class);

    private int expireTime;

    private MemcachedClient cache;

    public int getExpireTime() {
        return expireTime;
    }

    @Required
    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public MemcachedClient getCache() {
        return cache;
    }

    @Required
    public void setCache(MemcachedClient cache) {
        this.cache = cache;
    }

    @Override
    public boolean contains(String k) {
        return cache.get(k) != null;
    }

    @Override
    public void putAll(Map<String, V> collection) {
        for (Map.Entry<String, V> entry : collection.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void put(Pair<String, V> entry) {
        put(entry.getFirst(), entry.getSecond());
    }

    @Override
    public void put(String k, V v) {
        cache.add(k, expireTime, v);
    }

    @Override
    public V get(String key) {
        long start = System.currentTimeMillis();

        V result = (V) cache.get(key);
        if (logger.isDebugEnabled()) {
            logger.debug("Time to retrieve from cache: " + (System.currentTimeMillis() - start) + " ms");
        }
        if (result == null) {
            //reference is gone so remove from cache.....
            if (logger.isInfoEnabled()) {
                logger.info("Reference to object associated with cache element [" + key
                        + "] garbage collected remove from cache");
            }
            flush(key);
        }
        return result;
    }

    @Override
    public void flushAll() {
        cache.flush();
    }

    @Override
    public void flush(String key) {
        cache.delete(key);
    }

    @Override
    public Collection<String> keys() {
        throw new UnsupportedOperationException("Not available");
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException("Not available");
    }
}