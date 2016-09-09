package com.bzn.fundamental.common.promise;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

import org.jdeferred.Deferred;

public class PromiseExecutor extends PromiseEntity<Void> {

    public Deferred<Void, Exception, Void> execute() {
        return resolve(null);
    }
}