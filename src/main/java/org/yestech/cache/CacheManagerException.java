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
package org.yestech.cache;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class CacheManagerException extends RuntimeException {

    public CacheManagerException() {
    }

    public CacheManagerException(String message) {
        super(message);
    }

    public CacheManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheManagerException(Throwable cause) {
        super(cause);
    }
}
