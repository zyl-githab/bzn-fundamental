package com.bzn.fundmental.mongodb.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author：dzz
 * @since： 2016年09月13 上午11:21
 * @version:
 * 受益信息
 */
@XmlRootElement
public class Benefit implements Serializable {

    private static final long serialVersionUID = 7008981382668198423L;

    /*受益人姓名*/
    private String name;

    /*受益人顺序*/
    private int order;

    /*受益人拼音名称*/
    private String namePinyin;

    /*受益人英文名称*/
    private String englishName;

    /*受益人证件类型*/
    private String certType;

    /*受益人证件号*/
    private String certNo;

    /*受益人手机号*/
    private String mobile;

    /*受益人邮箱*/
    private String email;

    /*受益人生日*/
    private String birthday;

    /*受益人性别*/
    private String gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
