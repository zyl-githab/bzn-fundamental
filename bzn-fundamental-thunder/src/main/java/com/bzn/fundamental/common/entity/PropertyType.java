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

public enum PropertyType {
    REMOTE("remote"),
    LOCAL("local");

    private String value;

    private PropertyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    public static PropertyType fromString(String value) {
        for (PropertyType type : PropertyType.values()) {
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