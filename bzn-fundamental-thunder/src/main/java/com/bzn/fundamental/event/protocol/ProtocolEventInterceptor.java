package com.bzn.fundamental.event.protocol;

import com.bzn.fundamental.event.eventbus.EventControllerFactory;
import com.bzn.fundamental.event.eventbus.EventControllerType;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Neptune
 * @email 1394997@qq.com
 * @version 1.0
 */

import com.google.common.eventbus.Subscribe;

public abstract class ProtocolEventInterceptor {
    public ProtocolEventInterceptor() {
        EventControllerFactory.getSingletonController(EventControllerType.ASYNC).register(this);
    }

    @Subscribe
    public void listen(ProtocolEvent event) {
        onEvent(event);
    }
    
    protected abstract void onEvent(ProtocolEvent event);
}