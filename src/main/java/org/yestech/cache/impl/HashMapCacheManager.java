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

import org.springframework.beans.factory.annotation.Required;
import org.terracotta.modules.annotations.AutolockRead;
import org.terracotta.modules.annotations.AutolockWrite;
import org.terracotta.modules.annotations.Root;
import org.yestech.cache.ICacheManager;
import org.yestech.lib.util.Pair;

import java.util.Map;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class HashMapCacheManager implements ICacheManager {

    @Root
    private Map cache;

    public Map getCache() {
        return cache;
    }

    @Required
    public void setCache(Map cache) {
        this.cache = cache;
    }

    @Override
    @AutolockRead
    public <K> boolean contains(K k) {
        synchronized (cache) {
            return cache.containsKey(k);
        }
    }


    @Override
    @AutolockWrite
    public <V, K> void put(Pair<K, V> entry) {
        put(entry.getFirst(), entry.getSecond());
    }

    @Override
    @AutolockWrite
    public <V, K> void put(K k, V v) {
        synchronized (cache) {
            cache.put(k, v);
        }
    }

    @Override
    @AutolockRead
    public <V, K> V get(K key) {
        synchronized (cache) {
            return (V) cache.get(key);
        }
    }

    @Override
    @AutolockWrite
    public void flushAll() {
        synchronized (cache) {
            cache.clear();
        }
    }

    @Override
    @AutolockWrite
    public <K> void flush(K key) {
        synchronized (cache) {
            cache.remove(key);
        }
    }
}
