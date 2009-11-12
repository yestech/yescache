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

import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache Manager for Java {@link ConcurrentHashMap}
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
public class ConcurrentHashMapCacheManager<K,V> extends ConcurrentMapCacheManager<K,V> {
    public ConcurrentHashMapCacheManager() {
        super();
        setCache(new ConcurrentHashMap<K,V>());
    }
}