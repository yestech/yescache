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
import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.yestech.cache.ICacheManager;
import org.yestech.lib.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import java.util.Set;

/**
 * Cache Manager for Apache <a href="http://http://jakarta.apache.org/jcs/">jcs</a>
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
public class JCSCacheManager<K,V> implements ICacheManager<K,V>
{
    final private static Logger logger = LoggerFactory.getLogger(JCSCacheManager.class);
    private JCS cache;

    public JCSCacheManager()
    {
    }

    public JCS getCache()
    {
        return cache;
    }

    @Required
    public void setCache(JCS cache)
    {
        this.cache = cache;
    }

    @Override
    public V get(K key)
    {
        long start = System.currentTimeMillis();
        Object cachedValue = cache.get(key);
        if (logger.isDebugEnabled())
        {
            logger.debug("Time to retrieve from cache: " + (System.currentTimeMillis() - start) + " ms");
        }
        if (cachedValue == null)
        {
            //reference is gone so remove from cache.....
            if (logger.isInfoEnabled())
            {
                logger.info("Reference to object associated with cache element [" + key
                            + "] garbage collected remove from cache");
            }
            flush(key);
        }
        return (V) cachedValue;
    }

    @Override
    public void flushAll()
    {
        try
        {
            cache.clear();
        }
        catch (CacheException e)
        {
            logger.error("error flushing all...", e);
        }
    }

    @Override
    public void flush(K key)
    {
        try
        {
            cache.remove(key);
        }
        catch (CacheException e)
        {
            logger.error("error flushing key: " + key, e);
        }
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
        try
        {
            cache.put(k, v);
        }
        catch (CacheException e)
        {
            logger.error("error putting entry[" + k + "]: " + v, e);
        }
    }

    @Override
    public boolean contains(K k) {
        return cache.get(k) != null;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("not yet implemented...");
    }

    @Override
    public Set<V> getAll() {
        throw new UnsupportedOperationException("not yet implemented...");
    }
}