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

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public interface ICacheManager<K, V> {

    boolean contains(K k);

    void put(Pair<K, V> entry);

    void put(K k, V v);

    void putAll(Map<K,V> collection);

//    void putAll(Collection<IPair<K,V>> collection);

    V get(K key);

    void flushAll();

    void flush(K key);

    Set<K> keySet();

    Set<V> getAll();

}