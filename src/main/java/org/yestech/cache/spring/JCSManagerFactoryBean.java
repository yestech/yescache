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

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.io.Resource;
import org.apache.jcs.engine.control.CompositeCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class JCSManagerFactoryBean implements InitializingBean, DisposableBean
{
    final private static Logger logger = LoggerFactory.getLogger(JCSManagerFactoryBean.class);
    
    private Resource configLocation;
    private CompositeCacheManager ccm;

    public void setConfigLocation(Resource configLocation)
    {
        this.configLocation = configLocation;
    }

    public void afterPropertiesSet() throws IOException
    {
        ccm = CompositeCacheManager.getUnconfiguredInstance();
        Properties props = new Properties();
        props.load(this.configLocation.getInputStream());
        ccm.configure(props);
    }

    public boolean isSingleton()
    {
        return true;
    }

    public void destroy() throws Exception
    {
        if (ccm != null)
        {
            for (String cacheName : ccm.getCacheNames())
            {
                ccm.freeCache(cacheName);
            }
            ccm.shutDown();
        }
    }

    public Object getObject() throws Exception
    {
        return ccm;
    }

    public Class getObjectType()
    {
        return (this.ccm != null ? this.ccm.getClass() : CompositeCacheManager.class);

    }
}
