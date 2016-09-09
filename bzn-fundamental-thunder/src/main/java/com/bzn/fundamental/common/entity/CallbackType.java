package com.bzn.fundamental.common.entity;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

public enum CallbackType {
    CALLBACK("callback"),
    PROMISE("promise");

    private String value;

    private CallbackType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    public static CallbackType fromString(String value) {
        for (CallbackType type : CallbackType.values()) {
            if (type.getValue().equalsIgnoreCase(value.trim())) {
                return type;
            }
        }
        
        throw new IllegalArgumentException("Mismatched type with value=" + value);
    }

    @Override
    public String toString() {
        return value;
    }
}