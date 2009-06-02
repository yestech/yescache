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

import org.yestech.cache.ICacheManager;
import org.yestech.lib.util.Pair;
import org.springframework.beans.factory.annotation.Required;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.util.Set;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
public class EhCacheCacheManager implements ICacheManager {
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
    public <K> boolean contains(K k) {
        return cache.isKeyInCache(k);
    }

    @Override
    public <V, K> void put(Pair<K, V> entry) {
        put(entry.getFirst(), entry.getSecond());
    }

    @Override
    public <V, K> void put(K k, V v) {
        cache.put(new Element(k, v));
    }

    @Override
    public <V, K> V get(K key) {
        long start = System.currentTimeMillis();

        Element element = cache.get(key);
        if (logger.isDebugEnabled())
        {
            logger.debug("Time to retrieve from cache: " + (System.currentTimeMillis() - start) + " ms");
        }
        if (element == null || element.getValue() == null)
        {
            //reference is gone so remove from cache.....
            if (logger.isInfoEnabled())
            {
                logger.info("Reference to object associated with cache element [" + key
                            + "] garbage collected remove from cache");
            }
            flush(key);
        }
        return (V) element.getValue();
    }

    @Override
    public void flushAll() {
        cache.flush();
    }

    @Override
    public <K> void flush(K key) {
        cache.remove(key);
    }

    @Override
    public <K> Set<K> keySet() {
        return (Set<K>)cache.getKeys();
    }

    @Override
    public <V> Set<V> getAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
