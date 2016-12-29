package com.bzn.fundmental.mongodb.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author：dzz @since： 2016年09月13 上午9:44
 * @version: 保单信息
 */
@XmlRootElement
public class Policy implements Serializable {

	private static final long serialVersionUID = -2776448558653312198L;

	private String id;

	private String policyId;

	/* 投保单号 */
	private String proposalNo;

	/* 保单号 */
	private String policyNo;

	/* 保险公司代码 */
	private String insureCompanyCode;

	/* 方案代码 */
	private String planCode;

	/* 产品代码 */
	private String productCode;

	/* 保费 */
	private BigDecimal premium;

	/* 保额 */
	private BigDecimal amount;

	/* 保单生效日期 */
	private Date effectDate;

	/* 保险起期 */
	private Date startDate;

	/* 保险止期 */
	private Date endDate;

	/* 保单状态 */
	private String status;

	/* 平台1:官网2:接口3:百度外卖 */
	private String platform;

	/* 投保信息 */
	private Holder holder;

	/* 被保险信息 */
	private List<Insured> insureds;

	/* 方案信息 */
	private List<Product> products;

	/* 理赔区分ofo、kfk、百度众包、兼职宝 */
	private String userId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getInsureCompanyCode() {
		return insureCompanyCode;
	}

	public void setInsureCompanyCode(String insureCompanyCode) {
		this.insureCompanyCode = insureCompanyCode;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public BigDecimal getPremium() {
		return premium;
	}

	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Holder getHolder() {
		return holder;
	}

	public void setHolder(Holder holder) {
		this.holder = holder;
	}

	public List<Insured> getInsureds() {
		return insureds;
	}

	public void setInsureds(List<Insured> insureds) {
		this.insureds = insureds;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
