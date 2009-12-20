
/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 *  http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.cache;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Last Modified Date: $DateTime: $
 * @author Greg Crow
 * @version $Revision: $
 */
public class CachemanageExceptionUnitTest
{
    @Test(expected = CacheManagerException.class)
    public void testCachemangerException() {
        CacheManagerException ex = new CacheManagerException();
        assertTrue(ex instanceof RuntimeException);
        assertNull(ex.getMessage());

        ex = new CacheManagerException("testMessage");
        assertEquals("testMessage", ex.getMessage());

        ex = new CacheManagerException("anotherTest", new Exception("testing"));
        assertEquals("anotherTest", ex.getMessage());
        assertEquals("testing", ex.getCause().getMessage());

        ex = new CacheManagerException(new RuntimeException("finalTest"));
        assertEquals("finalTest", ex.getCause().getMessage());

        throw ex;
    }


}
