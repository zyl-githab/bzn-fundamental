package com.bzn.fundamental.monitor;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

import java.util.List;

import com.bzn.fundamental.common.entity.MonitorStat;

public interface MonitorRetriever {
    
    // 解析Json成MonitorStat对象
    MonitorStat create(String monitorStatJson);

    // 根据时间排序
    void sort(List<MonitorStat> monitorStatList);
}