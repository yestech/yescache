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
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.tc.cache.CacheFactory;
import org.tc.cache.ITerracottaCache;
import org.tc.cache.TerracottaTreeCache;

import java.io.IOException;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class TreeMapCacheFactoryBean implements FactoryBean, BeanNameAware, InitializingBean
{

    final private static Logger logger = LoggerFactory.getLogger(TreeMapCacheFactoryBean.class);

    private String cacheName;
    private String beanName;
    private ITerracottaCache cache;

    /**
     * Set a name for which to retrieve or create a cache instance.
     * Default is the bean name of this EhCacheFactoryBean.
     */
    @Required
    public void setCacheName(String cacheName)
    {
        this.cacheName = cacheName;
    }

    public void setBeanName(String name)
    {
        this.beanName = name;
    }


    public void afterPropertiesSet() throws IOException
    {
        // If no CacheManager given, fetch the default.
        // If no cache name given, use bean name as cache name.
        if (this.cacheName == null)
        {
            this.cacheName = this.beanName;
        }

            this.cache = CacheFactory.getInstance().getCache(cacheName,CacheFactory.CACHE_TYPE_POJOCACHE, TerracottaTreeCache.CONCURRENTHASHMAP,true);
    }

    public Object getObject()
    {
        return this.cache;
    }

    public Class getObjectType()
    {
        return (this.cache != null ? this.cache.getClass() : ITerracottaCache.class);
    }

    public boolean isSingleton()
    {
        return true;
    }

}