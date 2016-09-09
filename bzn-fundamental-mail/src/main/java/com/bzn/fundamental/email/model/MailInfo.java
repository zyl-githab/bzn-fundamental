package com.bzn.fundamental.email.model;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

/**
 * 邮件主体
 * 
 * @author：fengli
 * @since：2016年8月24日 下午3:17:00
 * @version:
 */
public class MailInfo {

	// 发送邮件的服务器的IP和端口
	private String mailServerHost;

	private String mailServerPort = "25";

	// 邮件发送者的地址
	private String fromAddress;

	// 邮件接收者的地址
	private String toAddress;

	// 邮件抄送接收者的地址
	private String toCCAddress;

	// 登陆邮件发送服务器的用户名和密码
	private String userName;

	// 昵称
	private String nickName;

	private String password;

	// 是否需要身份验证
	private boolean validate = true;

	// 是否需要ssl支持
	private boolean validateSSL = false;

	// 邮件主题
	private String subject;

	// 邮件的文本内容
	private String content;

	// 邮件附件的文件名
	private List<FileInputStream> attachInputStreams;

	private List<String> attachFileNames;

	public Properties getProperties() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", this.getMailServerHost());
		properties.put("mail.smtp.port", this.getMailServerPort());
		properties.put("mail.smtp.auth", this.validate ? "true" : "false");
		return properties;
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getToCCAddress() {
		return toCCAddress;
	}

	public void setToCCAddress(String toCCAddress) {
		this.toCCAddress = toCCAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public boolean isValidateSSL() {
		return validateSSL;
	}

	public void setValidateSSL(boolean validateSSL) {
		this.validateSSL = validateSSL;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<FileInputStream> getAttachInputStreams() {
		return attachInputStreams;
	}

	public void setAttachInputStreams(List<FileInputStream> attachInputStreams) {
		this.attachInputStreams = attachInputStreams;
	}

	public List<String> getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(List<String> attachFileNames) {
		this.attachFileNames = attachFileNames;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
