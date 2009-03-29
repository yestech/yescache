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
package org.yestech.cache.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tc.cache.CacheFactory;
import org.tc.cache.ITerracottaCache;
import org.tc.cache.TerracottaPojoCache;
import org.tc.cache.TerracottaTreeCache;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class PojoCacheFactoryBean extends TerracottaCacheFactoryBean {

    final private static Logger logger = LoggerFactory.getLogger(PojoCacheFactoryBean.class);

    @Override
    protected Class<? extends ITerracottaCache> getCacheType() {
        return TerracottaPojoCache.class;
    }

    @Override
    protected ITerracottaCache getCacheInstance() {
        return CacheFactory.getInstance().getCache(getCacheName(), CacheFactory.CACHE_TYPE_POJOCACHE, TerracottaTreeCache.CONCURRENTHASHMAP, true);
    }

}