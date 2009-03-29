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

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.tc.cache.CacheFactory;
import org.tc.cache.ITerracottaCache;

import java.io.IOException;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public abstract class TerracottaCacheFactoryBean implements FactoryBean, InitializingBean, DisposableBean {

    private String cacheName;
    private ITerracottaCache cache;

    /**
     * Set a name for which to retrieve or create a cache instance.
     * Default is the bean name of this EhCacheFactoryBean.
     */
    @Required
    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getCacheName() {
        return cacheName;
    }

    public ITerracottaCache getCache() {
        return cache;
    }

    public void afterPropertiesSet() throws IOException {
        this.cache = getCacheInstance();
    }

    protected abstract ITerracottaCache getCacheInstance();

    public Object getObject() {
        return this.cache;
    }

    public Class getObjectType() {
        return (this.cache != null ? this.cache.getClass() : getCacheType());
    }

    protected abstract Class<? extends ITerracottaCache> getCacheType();

    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() throws Exception {
        CacheFactory.getInstance().destroyCache(cacheName);
    }
}