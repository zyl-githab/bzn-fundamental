package com.bzn.fundmental.mongodb.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author：dzz
 * @since： 2016年09月13 上午9:49
 * @version:
 * 被保险人子女信息
 */
@XmlRootElement
public class InsuredChild implements Serializable {

    private static final long serialVersionUID = -6830040403965221877L;

    /*子女姓名*/
    private String childName;

    /*子女性别*/
    private String childGender;

    /*子女证件类型*/
    private String childCertType;

    /*子女证件号*/
    private String childCertNo;

    /*子女生日*/
    private String childBirthday;

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildGender() {
        return childGender;
    }

    public void setChildGender(String childGender) {
        this.childGender = childGender;
    }

    public String getChildCertType() {
        return childCertType;
    }

    public void setChildCertType(String childCertType) {
        this.childCertType = childCertType;
    }

    public String getChildCertNo() {
        return childCertNo;
    }

    public void setChildCertNo(String childCertNo) {
        this.childCertNo = childCertNo;
    }

    public String getChildBirthday() {
        return childBirthday;
    }

    public void setChildBirthday(String childBirthday) {
        this.childBirthday = childBirthday;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
