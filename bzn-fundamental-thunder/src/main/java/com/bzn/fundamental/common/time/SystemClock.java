package com.bzn.fundamental.common.time;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

// System.currentTimeMillis()是比较重的调用，调用时候要进行内核态/用户态切换，应该让真正在忙着的那些线程尽量少切换
// SystemClock的时间精度不那么高，所以不能用在要求时间非常准确的场景
public class SystemClock {
    private static final SystemClock SYSTEM_CLOCK = new SystemClock(1);

    private final long precision;
    private final AtomicLong now;

    public SystemClock(long precision) {
        this.precision = precision;

        now = new AtomicLong(System.currentTimeMillis());
        scheduleClockUpdating();
    }

    private void scheduleClockUpdating() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "System Clock");
                thread.setDaemon(true);

                return thread;
            }
        });
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                now.set(System.currentTimeMillis());
            }
        }, precision, precision, TimeUnit.MILLISECONDS);
    }

    public long now() {
        return now.get();
    }

    public long precision() {
        return precision;
    }

    public static SystemClock instance() {
        return SYSTEM_CLOCK;
    }
}