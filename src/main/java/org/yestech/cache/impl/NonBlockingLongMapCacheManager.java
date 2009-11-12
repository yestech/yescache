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

import org.cliffc.high_scale_lib.NonBlockingHashMapLong;

/**
 * Cache Manager for Cliff Click's Scalable Non-Blocking HashMap
 * {@link org.cliffc.high_scale_lib.NonBlockingHashMapLong}
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
public class NonBlockingLongMapCacheManager<Long, V> extends ConcurrentMapCacheManager<Long, V> {

    public NonBlockingLongMapCacheManager() {
        super();
        setCache(new NonBlockingHashMapLong<V>());
    }
}