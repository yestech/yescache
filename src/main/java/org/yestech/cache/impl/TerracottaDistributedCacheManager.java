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
import org.yestech.cache.ICacheManager;
import org.yestech.lib.util.Pair;
import org.terracotta.modules.annotations.Root;
import org.terracotta.modules.annotations.InstrumentedClass;
import org.terracotta.cache.CacheConfigFactory;
import org.terracotta.cache.DistributedCache;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Set;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.terracotta.cache.CacheConfig;
import static com.google.common.collect.Sets.newHashSet;

/**
 * {@link org.yestech.cache.ICacheManager} that allows access to Terracotta {@link DistributedCache}
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
@InstrumentedClass
public class TerracottaDistributedCacheManager<K,V> implements ICacheManager<K,V> {

    @Root
    private DistributedCache cache;
    private int maxTTIInSeconds;
    private int maxTTLInSeconds;
    private boolean orphanEvictionEnabled;
    private int orphanEvictionFrequency;
    private boolean enableLogging;
    private int targetMaxInMemoryCount;
    private int targetMaxTotalCount;
    private String name;

    @PostConstruct
    public void start() {
        CacheConfig builder =  CacheConfigFactory.newConfig();
        if (maxTTIInSeconds > 0) {
            builder = builder.setMaxTTISeconds(maxTTIInSeconds);
        }
        if (maxTTLInSeconds > 0) {
            builder = builder.setMaxTTLSeconds(maxTTLInSeconds);
        }
        if (orphanEvictionEnabled) {
            builder = builder.setOrphanEvictionEnabled(orphanEvictionEnabled);
        }
        if (orphanEvictionFrequency > 0) {
            builder = builder.setOrphanEvictionPeriod(orphanEvictionFrequency);
        }
        if (targetMaxInMemoryCount > 0) {
            builder = builder.setTargetMaxInMemoryCount(targetMaxInMemoryCount);
        }
        if (targetMaxTotalCount > 0) {
            builder = builder.setTargetMaxTotalCount(targetMaxTotalCount);
        }
        if (enableLogging) {
            builder = builder.setLoggingEnabled(enableLogging);
        }
        if (StringUtils.isNotBlank(name)) {
            builder.setName(name);
        }
        cache = builder.newCache();
    }

    @PreDestroy
    public void stop() {
        if (cache != null) {
            cache.shutdown();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTargetMaxInMemoryCount() {
        return targetMaxInMemoryCount;
    }

    public void setTargetMaxInMemoryCount(int targetMaxInMemoryCount) {
        this.targetMaxInMemoryCount = targetMaxInMemoryCount;
    }

    public int getTargetMaxTotalCount() {
        return targetMaxTotalCount;
    }

    public void setTargetMaxTotalCount(int targetMaxTotalCount) {
        this.targetMaxTotalCount = targetMaxTotalCount;
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

    public boolean isEnableLogging() {
        return enableLogging;
    }

    public void setEnableLogging(boolean enableLogging) {
        this.enableLogging = enableLogging;
    }

    public int getMaxTTIInSeconds() {
        return maxTTIInSeconds;
    }

    public void setMaxTTIInSeconds(int maxTTIInSeconds) {
        this.maxTTIInSeconds = maxTTIInSeconds;
    }

    public int getMaxTTLInSeconds() {
        return maxTTLInSeconds;
    }

    public void setMaxTTLInSeconds(int maxTTLInSeconds) {
        this.maxTTLInSeconds = maxTTLInSeconds;
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
        cache.putNoReturn(k, v);
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
        cache.removeNoReturn(key);
    }

    @Override
    public Collection<K> keys() {
        return cache.keySet();
    }

    @Override
    public Collection<V> getAll() {
        Set<V> values = newHashSet();
        Collection<K> keys = keys();
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
