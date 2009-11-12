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

import org.cliffc.high_scale_lib.NonBlockingIdentityHashMap;

/**
 * Cache Manager for Cliff Click's <a href="http://sourceforge.net/projects/high-scale-lib/">Scalable Non-Blocking HashMap</a>
 * {@link org.cliffc.high_scale_lib.NonBlockingIdentityHashMap}
 *
 *
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
public class NonBlockingIdentityMapCacheManager<K,V> extends ConcurrentMapCacheManager<K,V> {

    public NonBlockingIdentityMapCacheManager() {
        super();
        setCache(new NonBlockingIdentityHashMap<K,V>());
    }
}