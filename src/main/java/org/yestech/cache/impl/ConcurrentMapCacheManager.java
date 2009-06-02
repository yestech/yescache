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

import java.util.concurrent.ConcurrentMap;
import java.util.Set;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
public class ConcurrentMapCacheManager implements ICacheManager {

    @Root
    private ConcurrentMap cache;

    public ConcurrentMap getCache() {
        return cache;
    }

    @Required
    public void setCache(ConcurrentMap cache) {
        this.cache = cache;
    }

    @Override
    @AutolockRead
    public <K> boolean contains(K k) {
        return cache.containsKey(k);
    }


    @Override
    @AutolockWrite
    public <V, K> void put(Pair<K, V> entry) {
        put(entry.getFirst(), entry.getSecond());
    }

    @Override
    @AutolockWrite
    public <V, K> void put(K k, V v) {
        cache.put(k, v);
    }

    @Override
    @AutolockRead
    public <V, K> V get(K key) {
        return (V) cache.get(key);
    }

    @Override
    @AutolockWrite
    public void flushAll() {
        cache.clear();
    }

    @Override
    @AutolockWrite
    public <K> void flush(K key) {
        cache.remove(key);
    }

    @Override
    @AutolockRead
    public <K> Set<K> keySet() {
        return (Set<K>)cache.keySet();
    }

    @Override
    @AutolockRead
    public <V> Set<V> getAll() {
        return (Set<V>)cache.entrySet();
    }
}