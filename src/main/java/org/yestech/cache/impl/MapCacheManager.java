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
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class MapCacheManager implements ICacheManager {

    @Root
    private Map cache;
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private Lock readLock = rwl.readLock();
    private Lock writeLock = rwl.writeLock();

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
        readLock.lock();
        try {
            return cache.containsKey(k);
        } finally {
            readLock.unlock();
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
        writeLock.lock();
        try {
            cache.put(k, v);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    @AutolockRead
    public <V, K> V get(K key) {
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
    public <K> void flush(K key) {
        writeLock.lock();
        try {
            cache.remove(key);
        } finally {
            writeLock.unlock();
        }
    }
}
