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

import org.terracotta.modules.annotations.AutolockRead;
import org.terracotta.modules.annotations.AutolockWrite;
import org.terracotta.modules.annotations.InstrumentedClass;
import org.terracotta.modules.annotations.Root;
import org.yestech.cache.ICacheManager;
import org.yestech.lib.util.Pair;

import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Cache Manager for {@link Map}
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
@InstrumentedClass
public class MapCacheManager<K,V> implements ICacheManager<K,V> {

    @Root
    private Map<K,V> cache;
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private Lock readLock = rwl.readLock();
    private Lock writeLock = rwl.writeLock();

    public Map getCache() {
        return cache;
    }

    public void setCache(Map cache) {
        this.cache = cache;
    }

    @Override
    @AutolockRead
    public boolean contains(K k) {
        readLock.lock();
        try {
            return cache.containsKey(k);
        } finally {
            readLock.unlock();
        }
    }


    @Override
    @AutolockWrite
    public void putAll(Map<K, V> collection) {
        writeLock.lock();
        try {
            cache.putAll(collection);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    @AutolockWrite
    public void put(Pair<K, V> entry) {
        put(entry.getFirst(), entry.getSecond());
    }

    @Override
    @AutolockWrite
    public void put(K k, V v) {
        writeLock.lock();
        try {
            cache.put(k, v);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    @AutolockRead
    public V get(K key) {
        readLock.lock();
        try {
            return (V) cache.get(key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @AutolockWrite
    public void flushAll() {
        writeLock.lock();
        try {
            cache.clear();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    @AutolockWrite
    public void flush(K key) {
        writeLock.lock();
        try {
            cache.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Collection<K> keys() {
        readLock.lock();
        try {
            return cache.keySet();
        } finally {
            readLock.unlock();
        }

    }

    @Override
    public Collection<V> values() {
        readLock.lock();
        try {
            return cache.values();
        } finally {
            readLock.unlock();
        }
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
