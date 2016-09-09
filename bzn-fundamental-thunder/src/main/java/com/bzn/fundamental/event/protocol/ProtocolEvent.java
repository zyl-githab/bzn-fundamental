package com.bzn.fundamental.event.protocol;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.bzn.fundamental.common.constant.ThunderConstants;
import com.bzn.fundamental.common.entity.ActionType;
import com.bzn.fundamental.common.entity.ApplicationType;
import com.bzn.fundamental.common.entity.MethodKey;
import com.bzn.fundamental.common.entity.ProtocolType;
import com.bzn.fundamental.common.util.ExceptionUtil;
import com.bzn.fundamental.event.eventbus.Event;
import com.bzn.fundamental.protocol.ProtocolMessage;

public class ProtocolEvent extends Event implements Serializable {
    private static final long serialVersionUID = 8471746963028869465L;

    // 标识服务提供方，服务调用方
    private ApplicationType applicationType;

    // 标识系统事件，生产事件，消费事件
    private ActionType actionType;

    // 标识协议类型
    private ProtocolType protocolType;

    // 标识协议事件
    private ProtocolMessage protocolMessage;

    public ProtocolEvent(ApplicationType applicationType, ActionType actionType, ProtocolType protocolType, ProtocolMessage protocolMessage) {
        super();

        this.applicationType = applicationType;
        this.actionType = actionType;
        this.protocolType = protocolType;
        this.protocolMessage = protocolMessage;
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public ProtocolType getProtocolType() {
        return protocolType;
    }

    public ProtocolMessage getProtocolMessage() {
        return protocolMessage;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("applicationType", applicationType);
        map.put("actionType", actionType);
        map.put("protocolType", protocolType);
        if (actionType != ActionType.SYSTEM) {
            map.put("traceId", protocolMessage.getTraceId());
            map.put("messageId", protocolMessage.getMessageId());
            map.put("fromCluster", protocolMessage.getFromCluster());
        }
        map.put("fromUrl", protocolMessage.getFromUrl());
        if (actionType != ActionType.SYSTEM) {
            map.put("toCluster", protocolMessage.getToCluster());
        }
        map.put("toUrl", protocolMessage.getToUrl());
        if (actionType != ActionType.SYSTEM) {
            map.put("processStartTime", new SimpleDateFormat(ThunderConstants.DATE_FORMAT).format(new Date(protocolMessage.getProcessStartTime())));
            map.put("processEndTime", new SimpleDateFormat(ThunderConstants.DATE_FORMAT).format(new Date(protocolMessage.getProcessEndTime())));
            map.put("deliverStartTime", new SimpleDateFormat(ThunderConstants.DATE_FORMAT).format(new Date(protocolMessage.getDeliverStartTime())));
            map.put("deliverEndTime", new SimpleDateFormat(ThunderConstants.DATE_FORMAT).format(new Date(protocolMessage.getDeliverEndTime())));
            map.put("processedTime", (protocolMessage.getProcessEndTime() - protocolMessage.getProcessStartTime()) + " ms");
            map.put("deliveredTime", (protocolMessage.getDeliverEndTime() - protocolMessage.getDeliverStartTime()) + " ms");
            map.put("totalTime", (protocolMessage.getProcessEndTime() - protocolMessage.getProcessStartTime() + protocolMessage.getDeliverEndTime() - protocolMessage.getDeliverStartTime()) + " ms");
            map.put("interface", protocolMessage.getInterface());
            map.put("method", protocolMessage.getMethod());
            map.put("parameterTypes", MethodKey.toParameterTypes(protocolMessage.getParameterTypes()));
            map.put("async", protocolMessage.isAsync());
            map.put("callback", protocolMessage.getCallback());
            map.put("timeout", protocolMessage.getTimeout());
            map.put("broadcast", protocolMessage.isBroadcast());
            map.put("feedback", protocolMessage.isFeedback());
        }
        map.put("exception", toException());

        return map;
    }
    
    public String toException() {
        return ExceptionUtil.toExceptionString(protocolMessage.getException());
    }
}