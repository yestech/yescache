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

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Cache Manager for Java {@link java.util.concurrent.ConcurrentSkipListMap}
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
public class ConcurrentSkipListMapCacheManager<K,V> extends ConcurrentMapCacheManager<K,V> {
    public ConcurrentSkipListMapCacheManager() {
        super();
        setCache(new ConcurrentSkipListMap<K,V>());
    }
}