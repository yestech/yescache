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
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class EhCacheCacheManagerUnitTest {
    @Mock
    private Cache mockCache;

    private EhCacheCacheManager cacheManager;

    @Before
    public void setUp() {
        cacheManager = new EhCacheCacheManager();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFlushAll() {
        doNothing().when(mockCache).removeAll();
        cacheManager.setCache(mockCache);
        cacheManager.flushAll();
        verify(mockCache).removeAll();
    }
}
