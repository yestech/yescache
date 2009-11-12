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

import org.terracotta.modules.annotations.InstrumentedClass;

import java.util.TreeMap;

/**
 * Cache Manager for {@link java.util.TreeMap}
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
@InstrumentedClass
public class TreeMapCacheManager<K,V> extends MapCacheManager<K,V> {

    public TreeMapCacheManager() {
        super();
        setCache(new TreeMap<K,V>());
    }
}