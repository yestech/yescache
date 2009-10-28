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
import static com.google.common.collect.Sets.newHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.tc.cache.CacheException;
import org.tc.cache.ITerracottaCache;
import org.yestech.cache.ICacheManager;
import org.yestech.lib.util.Pair;

import java.util.Set;
import org.terracotta.modules.annotations.InstrumentedClass;
import org.terracotta.modules.annotations.Root;

/**
 * {@link org.yestech.cache.ICacheManager} that allows access to {@link ITerracottaCache}
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
@InstrumentedClass
public class TerracottaCacheManager<K,V> implements ICacheManager<K,V> {

    final private static Logger logger = LoggerFactory.getLogger(TerracottaCacheManager.class);

    private String fqn;

    @Root
    private ITerracottaCache cache;

    public ITerracottaCache getCache() {
        return cache;
    }

    public String getFqn() {
        return fqn;
    }

    @Required
    public void setFqn(String fqn) {
        this.fqn = fqn;
    }

    @Required
    public void setCache(ITerracottaCache cache) {
        this.cache = cache;
    }

    @Override
    public boolean contains(K k) {
        try {
            return cache.exists(fqn, k);
        } catch (CacheException e) {
            return false;
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
        try {
            cache.put(fqn, k, v);
        } catch (CacheException e) {
            logger.error("error putting entry[" + k + "]: " + v, e);
        }
    }

    @Override
    public V get(K key) {
        long start = System.currentTimeMillis();
        Object cachedValue = null;
        try {
            cachedValue = cache.get(fqn, key);
        } catch (CacheException e) {
            logger.error("error getting key: " + key, e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Time to retrieve from cache: " + (System.currentTimeMillis() - start) + " ms");
        }
        if (cachedValue == null) {
            //reference is gone so remove from cache.....
            if (logger.isInfoEnabled()) {
                logger.info("Reference to object associated with cache element [" + key
                        + "] garbage collected remove from cache");
            }
            flush(key);
        }
        return (V) cachedValue;
    }

    @Override
    public void flushAll() {
        try {
            cache.remove(cache.getFqn());
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void flush(K key) {
        try {
            cache.remove(fqn, key);
        } catch (CacheException e) {
            logger.error("error flushing key: " + key, e);
        }
    }

    @Override
    public Set<K> keySet() {
        try {
            return (Set<K>) cache.getKeys(fqn);
        } catch (CacheException e) {
            logger.error("error getting key set", e);
        }
        return null;
    }

    @Override
    public Set<V> getAll() {
        Set<V> values = newHashSet();
        Set<K> keys = keySet();
        if (keys == null) {
            return null;
        } else {
            for (K key : keys) {
                values.add(get(key));
            }
        }
        return values;
    }
}
