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

import org.infinispan.Cache;
import org.infinispan.manager.CacheManager;
import org.infinispan.manager.DefaultCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * Factory for managing Infinispan Caches
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
public class InfinispanManagerFactoryBean implements FactoryBean, InitializingBean, DisposableBean {
    final private static Logger logger = LoggerFactory.getLogger(InfinispanManagerFactoryBean.class);

    private Resource configLocation;
    private CacheManager manager;

    @Required
    public void setConfigLocation(Resource configLocation) {
        this.configLocation = configLocation;
    }

    public void afterPropertiesSet() throws IOException {
        manager = new DefaultCacheManager(configLocation.getInputStream());
        manager.start();
    }

    public boolean isSingleton() {
        return true;
    }

    public void destroy() throws Exception {
        if (manager != null) {
            for (String cacheName : manager.getCacheNames()) {
                final Cache<Object, Object> cache = manager.getCache(cacheName);
                cache.stop();
            }
            manager.stop();
        }
    }

    public Object getObject() throws Exception {
        return manager;
    }

    public Class getObjectType() {
        return (this.manager != null ? this.manager.getClass() : CacheManager.class);

    }
}