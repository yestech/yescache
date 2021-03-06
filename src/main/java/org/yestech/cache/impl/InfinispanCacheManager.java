package org.yestech.cache.impl;

import org.yestech.cache.ICacheManager;
import org.yestech.lib.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.infinispan.Cache;

import java.util.Map;
import java.util.Set;
import java.util.Collection;


/**
 * An implements of a cache using <a href="http://www.jboss.org/infinispan">Infinispan</a> {@link Cache}.
 * 
 */
@SuppressWarnings("unchecked")
public class InfinispanCacheManager<K, V> implements ICacheManager<K, V> {
    final private static Logger logger = LoggerFactory.getLogger(EhCacheCacheManager.class);

    private Cache cache;

    public Cache getCache() {
        return cache;
    }

    @Required
    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @Override
    public boolean contains(K k) {
        return cache.containsKey(k);
    }

    @Override
    public void putAll(Map<K, V> collection) {
        for (Map.Entry<K, V> entry : collection.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void put(Pair<K, V> entry) {
        put(entry.getFirst(), entry.getSecond());
    }

    @Override
    public void put(K k, V v) {
        cache.putIfAbsent(k, v);
    }

    @Override
    public V get(K key) {
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
        cache.clear();
    }

    @Override
    public void flush(K key) {
        cache.remove(key);
    }

    @Override
    public Collection<K> keys() {
        return cache.keySet();
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    /**
     * Stores a cache to some type of durable storage.  Not gaurenteed to be implemented, dependant on the concrete
     * implementation.  This method should perform a NoOp if it is not supported.
     */
    @Override
    public void store() {
    }

    /**
     * Loads a cache from some type of durable storage.  Not gaurenteed to be implemented, dependant on the concrete
     * implementation.  This method should perform a NoOp if it is not supported.
     */
    @Override
    public void load() {
    }
}
