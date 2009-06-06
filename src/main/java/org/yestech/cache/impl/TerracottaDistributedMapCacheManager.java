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
import org.terracotta.modules.annotations.Root;
import org.terracotta.modules.dmap.DistributedMap;
import org.terracotta.modules.dmap.DistributedMapBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
public class TerracottaDistributedMapCacheManager implements ICacheManager {

    @Root
    private DistributedMap cache;
    private long maxTTIInMillis;
    private long maxTTLInMillis;
    private long evictorSleepInMillis;
    private int concurrency;
    private long orphanBatchPauseMillis;
    private int orphanBatchSize;
    private boolean orphanEvictionEnabled;
    private int orphanEvictionFrequency;
    private boolean evictorLogging;
    private boolean enableLogging;

    @PostConstruct
    public void start() {
        DistributedMapBuilder builder = new DistributedMapBuilder();
        if (maxTTIInMillis > 0) {
            builder = builder.setMaxTTIMillis(maxTTIInMillis);
        }
        if (maxTTLInMillis > 0) {
            builder = builder.setMaxTTLMillis(maxTTLInMillis);
        }
        if (evictorSleepInMillis > 0) {
            builder = builder.setEvictorSleepMillis(evictorSleepInMillis);
        }
        if (concurrency > 0) {
            builder = builder.setConcurrency(concurrency);
        }
        if (orphanBatchPauseMillis > 0) {
            builder = builder.setOrphanBatchPauseMillis(orphanBatchPauseMillis);
        }
        if (orphanBatchSize > 0) {
            builder = builder.setOrphanBatchSize(orphanBatchSize);
        }
        if (orphanEvictionEnabled) {
            builder = builder.setOrphanEvictionEnabled(orphanEvictionEnabled);
        }
        if (orphanEvictionFrequency > 0) {
            builder = builder.setOrphanEvictionFrequency(orphanEvictionFrequency);
        }
        if (evictorLogging) {
            builder = builder.setEvictorLoggingEnabled(evictorLogging);
        }
        if (enableLogging) {
            builder = builder.setLoggingEnabled(enableLogging);
        }
        cache = builder.newMap();
        if (cache != null) {
            cache.start();
        }
    }

    @PreDestroy
    public void stop() {
        if (cache != null) {
            cache.shutdown();
        }
    }

    public long getOrphanBatchPauseMillis() {
        return orphanBatchPauseMillis;
    }

    public void setOrphanBatchPauseMillis(long orphanBatchPauseMillis) {
        this.orphanBatchPauseMillis = orphanBatchPauseMillis;
    }

    public long getOrphanBatchSize() {
        return orphanBatchSize;
    }

    public void setOrphanBatchSize(int orphanBatchSize) {
        this.orphanBatchSize = orphanBatchSize;
    }

    public long getOrphanEvictionFrequency() {
        return orphanEvictionFrequency;
    }

    public void setOrphanEvictionFrequency(int orphanEvictionFrequency) {
        this.orphanEvictionFrequency = orphanEvictionFrequency;
    }

    public boolean isOrphanEvictionEnabled() {
        return orphanEvictionEnabled;
    }

    public void setOrphanEvictionEnabled(boolean orphanEvictionEnabled) {
        this.orphanEvictionEnabled = orphanEvictionEnabled;
    }

    public boolean isEvictorLogging() {
        return evictorLogging;
    }

    public void setEvictorLogging(boolean evictorLogging) {
        this.evictorLogging = evictorLogging;
    }

    public boolean isEnableLogging() {
        return enableLogging;
    }

    public void setEnableLogging(boolean enableLogging) {
        this.enableLogging = enableLogging;
    }

    public int getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(int concurrency) {
        this.concurrency = concurrency;
    }

    public long getMaxTTIInMillis() {
        return maxTTIInMillis;
    }

    public void setMaxTTIInMillis(long maxTTIInMillis) {
        this.maxTTIInMillis = maxTTIInMillis;
    }

    public long getMaxTTLInMillis() {
        return maxTTLInMillis;
    }

    public void setMaxTTLInMillis(long maxTTLInMillis) {
        this.maxTTLInMillis = maxTTLInMillis;
    }

    public long getEvictorSleepInMillis() {
        return evictorSleepInMillis;
    }

    public void setEvictorSleepInMillis(long evictorSleepInMillis) {
        this.evictorSleepInMillis = evictorSleepInMillis;
    }

    @Override
    public <K> boolean contains(K k) {
        return cache.containsKey(k);
    }

    @Override
    public <V, K> void put(Pair<K, V> entry) {
        put(entry.getFirst(), entry.getSecond());
    }

    @Override
    public <V, K> void put(K k, V v) {
        cache.put(k, v);
    }

    @Override
    public <V, K> V get(K key) {
        return (V) cache.get(key);
    }

    @Override
    public void flushAll() {
        cache.clear();
    }

    @Override
    public <K> void flush(K key) {
        cache.remove(key);
    }

    @Override
    public <K> Set<K> keySet() {
        return cache.getKeys();
    }

    @Override
    public <V> Set<V> getAll() {
        Set values = newHashSet();
        Set<Object> keys = keySet();
        if (keys == null) {
            return null;
        } else {
            for (Object key : keys) {
                values.add(get(key));
            }
        }
        return values;
    }
}
