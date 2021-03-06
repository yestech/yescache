/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

/*
 *
 * Author:  Artie Copeland
 * Last Modified Date: $DateTime: $
 */
package org.yestech.cache.impl;

import java.util.Map;
import java.util.Map.Entry;
import org.yestech.cache.ICacheManager;
import org.yestech.lib.util.Pair;
import org.springframework.beans.factory.annotation.Required;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import javax.annotation.PreDestroy;
import java.util.Set;
import java.util.Collection;

/**
 * Cache Manager for <a href="http://ehcache.org">ehcache</a>
 * 
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
public class EhCacheCacheManager<K,V> implements ICacheManager<K,V> {

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
        return cache.isKeyInCache(k);
    }

    @Override
    public void putAll(Map<K, V> collection) {
        for (Entry<K,V> entry: collection.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void put(Pair<K, V> entry) {
        put(entry.getFirst(), entry.getSecond());
    }

    @Override
    public void put(K k, V v) {
        cache.put(new Element(k, v));
    }

    @Override
    public V get(K key) {
        long start = System.currentTimeMillis();
        V result = null;
        Element element = cache.get(key);
        if (logger.isDebugEnabled())
        {
            logger.debug("Time to retrieve from cache: " + (System.currentTimeMillis() - start) + " ms");
        }
        if (element == null || element.getValue() == null)
        {
            //reference is gone so remove from cache.....
            if (logger.isDebugEnabled())
            {
            	logger.debug("Referenced Element: " + element);
            	logger.debug("Reference to object associated with cache element [" + key
                            + "] garbage collected");
            }
        } else {
            result = (V) element.getValue();
        }
        return result;
    }

    @Override
    public void flushAll() {
        cache.removeAll();
    }

    @Override
    public void flush(K key) {
        cache.remove(key);
    }

    @Override
    public Collection<K> keys() {
        return cache.getKeys();
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
        cache.flush();
    }

    /**
     * Loads a cache from some type of durable storage.  Not gaurenteed to be implemented, dependant on the concrete
     * implementation.  This method should perform a NoOp if it is not supported.
     */
    @Override
    public void load() {
    }

}
