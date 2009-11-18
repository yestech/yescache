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
package org.yestech.cache;

import java.util.Map;
import org.yestech.lib.util.Pair;

import java.util.Set;
import java.util.Collection;

/**
 * Provides a encapsulated view to concrete Caching technologies.  By binding to this Interface concrete
 * Caching technologies are completely pluggable.
 *
 * @author Artie Copeland
 * @version $Revision: $
 * @param <K> Type of Keys stored in the cache
 * @param <V> Type of Values stored in the cache
 */
public interface ICacheManager<K, V> {

    /**
     * Checks is a Key is present in the cache.
     *
     * @param k the Key to check
     * @return true if present, else false
     */
    boolean contains(K k);

    /**
     * Puts a Key/Value into the cache, overriding any value the might already be associated with the Key.
     *
     * @param entry Key and Value pair to add (Key is {@link org.yestech.lib.util.Pair#getFirst()} and Value is
     * {@link org.yestech.lib.util.Pair#getSecond()}.
     */
    void put(Pair<K, V> entry);

    /**
     * Puts a Key/Value into the cache, overriding any value the might already be associated with the Key.
     *
     * @param k Key that the value will be associated by
     * @param v Value to be cached
     */
    void put(K k, V v);

    /**
     * Puts a Collection of Key/Value Pairs into the cache.
     *
     * @param collection Collection of Keys/Values to add
     */
    void putAll(Map<K,V> collection);

    /**
     * Return the Value associated with the Key
     *
     * @param key Key to lookup
     * @return The value, else null if no value is associated with the Key or the Key is not found
     */
    V get(K key);

    /**
     * Flushes the entire cache
     */
    void flushAll();

    /**
     * Flush the Value associated with the Key from the Cache
     *
     * @param key Key to flush
     */
    void flush(K key);

    /**
     * Returns all the Keys found in the Cache. Depending on the Concrete cache duplicates might be possible.
     *
     * @return All the keys as a collection
     */
    Collection<K> keys();

    /**
     * Returns all the values stored in the cache. Depending on the Concrete cache duplicates might be possible.
     *
      * @return All the values in the cache as a collection.
     */
    Collection<V> values();

}