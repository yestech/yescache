/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.cache.impl;

import net.sf.ehcache.Cache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 *
 */
@SuppressWarnings({"unchecked"})
@RunWith(MockitoJUnitRunner.class)
public class EhCacheCacheManagerUnitTest {
    
    EhCacheCacheManager cacheManager;

    @Before
    public void setUp() {
        cacheManager = new EhCacheCacheManager();
    }

    @Test
    public void testFlushAll() {
        Cache mockCache = mock(Cache.class);
        doNothing().when(mockCache).removeAll();
        cacheManager.setCache(mockCache);
        cacheManager.flushAll();
        verify(mockCache).removeAll();
    }

    @Test
    public void testPutGet() {
        Cache cache = new Cache("testCache", 10, false, false, 500, 2);

        try {
            cache.initialise();
            cacheManager.setCache(cache);
            cacheManager.put("foo", "bar");
            Object result = cacheManager.get("foo");
            assertNotNull(result);
            assertEquals("bar", result);

        } finally {
            cache.dispose();
        }
    }


}
