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

import org.yestech.lib.util.Pair;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public interface ICacheManager {

    <K> boolean contains(K k);

    <V,K> void put(Pair<K, V> entry);

    <V,K> void put(K k, V v);

    <V,K> V get(K key);

    void flushAll();

    <K> void flush(K key);
}