package com.bzn.fundmental.mongodb.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author：dzz
 * @since： 2016年09月13 上午9:47
 * @version:
 * 投保人
 */
@XmlRootElement
public class Holder implements Serializable {

    private static final long serialVersionUID = -1424995233388014008L;

    /*投保人名称*/
    private String name;

    /*投保人拼音名称*/
    private String namePinyin;

    /*投保人英文名称*/
    private String englishName;

    /*手机号*/
    private String mobile;

    /*投保人证件类型*/
    private String certType;

    /*投保人证件号*/
    private String certNo;

    /*联系人姓名*/
    private String contactName;

    /*联系人手机号*/
    private String contactMobile;

    /*联系人邮箱*/
    private String contactEmail;

    /*报案人姓名*/
    private String fnolName;

    /*报案人手机号*/
    private String fnolTel;

    /*报案邮箱*/
    private String fnolEmail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getFnolName() {
        return fnolName;
    }

    public void setFnolName(String fnolName) {
        this.fnolName = fnolName;
    }

    public String getFnolTel() {
        return fnolTel;
    }

    public void setFnolTel(String fnolTel) {
        this.fnolTel = fnolTel;
    }

    public String getFnolEmail() {
        return fnolEmail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setFnolEmail(String fnolEmail) {
        this.fnolEmail = fnolEmail;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
