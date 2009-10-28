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

import org.apache.jcs.JCS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.infinispan.Cache;
import org.infinispan.config.Configuration;
import org.infinispan.manager.CacheManager;

import java.io.IOException;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class InfinispanFactoryBean implements FactoryBean, InitializingBean {

    final private static Logger logger = LoggerFactory.getLogger(InfinispanFactoryBean.class);

    private String cacheName;
    private Cache cache;
    private CacheManager manager;

    @Required
    public void setManager(CacheManager manager) {
        this.manager = manager;
    }

    /**
     * Set a name for which to retrieve or create a cache instance.
     * Default is the bean name of this EhCacheFactoryBean.
     */
    @Required
    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public void afterPropertiesSet() throws IOException {
        this.cache = manager.getCache(cacheName);
        cache.start();
    }

    public Object getObject() {
        return this.cache;
    }

    public Class getObjectType() {
        return (this.cache != null ? this.cache.getClass() : Cache.class);
    }

    public boolean isSingleton() {
        return true;
    }

}