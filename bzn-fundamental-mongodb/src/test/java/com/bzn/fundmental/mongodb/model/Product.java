package com.bzn.fundmental.mongodb.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author：dzz
 * @since： 2016年09月13 上午9:50
 * @version:
 * 方案信息
 */
@XmlRootElement
public class Product implements Serializable {

    private static final long serialVersionUID = -8346765509026537886L;

    /*方案id*/
    private String skuId;

    /*方案code*/
    private String planCode;

    /*方案名称*/
    private String planName;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
