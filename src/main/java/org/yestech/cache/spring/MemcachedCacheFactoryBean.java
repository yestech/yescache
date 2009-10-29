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
import org.tc.cache.TerracottaTreeCache;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.CacheManager;
import org.infinispan.Cache;

import java.io.IOException;
import java.util.List;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.AddrUtil;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class MemcachedCacheFactoryBean implements FactoryBean, InitializingBean, DisposableBean {

    final private static Logger logger = LoggerFactory.getLogger(MemcachedCacheFactoryBean.class);
    private MemcachedClient client;

    private List<String> servers;

    public MemcachedClient getClient() {
        return client;
    }

    public void setClient(MemcachedClient client) {
        this.client = client;
    }

    public List<String> getServers() {
        return servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }

    public void afterPropertiesSet() throws IOException {
        //started by ctor
        String serverList = "";
        for (String server : servers) {
            serverList += server + " ";
        }
        serverList = serverList.trim();
        client = new MemcachedClient(AddrUtil.getAddresses(serverList));
    }

    public boolean isSingleton() {
        return true;
    }

    public void destroy() throws Exception {
        client.shutdown();
    }

    public Object getObject() throws Exception {
        return client;
    }

    public Class getObjectType() {
        return (this.client != null ? this.client.getClass() : MemcachedClient.class);

    }
}