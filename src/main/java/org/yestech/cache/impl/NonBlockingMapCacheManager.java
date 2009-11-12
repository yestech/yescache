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
import org.springframework.beans.factory.annotation.Required;
import org.terracotta.modules.annotations.AutolockRead;
import org.terracotta.modules.annotations.AutolockWrite;
import org.terracotta.modules.annotations.Root;
import org.yestech.cache.ICacheManager;
import org.yestech.lib.util.Pair;

import java.util.concurrent.ConcurrentMap;
import java.util.Set;
import org.terracotta.modules.annotations.InstrumentedClass;
import org.cliffc.high_scale_lib.NonBlockingHashMap;

/**
 * Cache Manager for Cliff Click's Scalable Non-Blocking HashMap
 * {@link NonBlockingHashMap}
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
public class NonBlockingMapCacheManager<K,V> implements ICacheManager<K,V> {

    private NonBlockingHashMap cache;

    public ConcurrentMap getCache() {
        return cache;
    }

    @Required
    public void setCache(NonBlockingHashMap cache) {
        this.cache = cache;
    }

    @Override
    public boolean contains(K k) {
        return cache.containsKey(k);
    }


    @Override
    public void putAll(Map<K, V> collection) {
        cache.putAll(collection);
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
        return (V) cache.get(key);
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
    public Set<K> keySet() {
        return (Set<K>)cache.keySet();
    }

    @Override
    public Set<V> getAll() {
        return (Set<V>)cache.entrySet();
    }
}