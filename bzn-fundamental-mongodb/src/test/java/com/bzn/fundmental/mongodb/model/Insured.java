package com.bzn.fundmental.mongodb.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * @author：dzz
 * @since： 2016年09月13 上午9:47
 * @version:
 * 被保险人
 */
@XmlRootElement
public class Insured implements Serializable {

    private static final long serialVersionUID = 214699737759201410L;

    /*被保险姓名*/
    private String name;

    /*投保人拼音名称*/
    private String namePinyin;

    /*投保人英文名称*/
    private String englishName;

    /*投保人证件类型*/
    private String certType;

    /*投保人证件号*/
    private String certNo;

    /*手机号*/
    private String mobile;

    /*邮箱*/
    private String email;

    /*被保险人生日*/
    private String birthday;

    /*被保险人性别*/
    private String gender;

    /*是否法定受益人*/
    private String isLegal;

    /*是否主被保人*/
    private String isChiefInsurer;

    /*连带被保险人与主被保人关系*/
    private String chiefInsureRelation;

    /*被保险人子女信息*/
    private List<InsuredChild> insuredChilds;

    /*受益人信息*/
    private List<Benefit> benefits;

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

    public String getIsLegal() {
        return isLegal;
    }

    public void setIsLegal(String isLegal) {
        this.isLegal = isLegal;
    }

    public String getIsChiefInsurer() {
        return isChiefInsurer;
    }

    public void setIsChiefInsurer(String isChiefInsurer) {
        this.isChiefInsurer = isChiefInsurer;
    }

    public String getChiefInsureRelation() {
        return chiefInsureRelation;
    }

    public void setChiefInsureRelation(String chiefInsureRelation) {
        this.chiefInsureRelation = chiefInsureRelation;
    }

    public List<InsuredChild> getInsuredChilds() {
        return insuredChilds;
    }

    public void setInsuredChilds(List<InsuredChild> insuredChilds) {
        this.insuredChilds = insuredChilds;
    }

    public List<Benefit> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<Benefit> benefits) {
        this.benefits = benefits;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
