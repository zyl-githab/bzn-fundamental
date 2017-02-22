package com.bzn.fundamental.email.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.bzn.fundamental.email.model.MailAuthenticator;
import com.bzn.fundamental.email.model.MailInfo;

/**
 * 邮件发送工具类
 * 
 * @author：fengli
 * @since：2016年8月24日 下午3:15:11
 * @version:
 */
public class MailUtil {

	/**
	 * logger:日志工具类
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MailUtil.class);

	// 邮件发送服务地址
	private static String mailServerHost;

	// 邮件发送端口
	private static String mailServerPort;

	// 邮件发送地址
	private static String fromAddress;

	// 邮件发送用户地址
	private static String userName;

	// 昵称
	private static String nickName;

	// 邮件发送用户密码
	private static String password;

	// 是否通过validate验证
	private static boolean validate;

	// 是否验证SSL
	private static boolean validateSSL;

	// 文件类型
	private static String contentType;

	static {
		try {
			Properties properties = new Properties();

			properties.load(new InputStreamReader(
					MailUtil.class.getClassLoader().getResourceAsStream("mail-config.properties"),
					"UTF-8"));

			mailServerHost = properties.getProperty("email.mailServerHost");
			mailServerPort = properties.getProperty("email.mailServerPort");
			fromAddress = properties.getProperty("email.fromAddress");
			userName = properties.getProperty("email.smtpUser");
			nickName = properties.getProperty("email.nickName");
			password = properties.getProperty("email.smtpPassword");
			validate = Boolean.valueOf(properties.getProperty("email.validate"));
			validateSSL = Boolean.valueOf(properties.getProperty("email.validateSSL"));
			contentType = properties.getProperty("email.contentType");
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}

	}

	/**
	 * 发送邮件
	 * 
	 * @param subject 主题
	 * @param content 发件内容
	 * @param toAddress 收件人
	 * @return
	 */
	public static boolean sendEmail(String subject, String content, String toAddress) {

		MailInfo mailInfo = new MailInfo();
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		mailInfo.setToAddress(toAddress);

		return sendTextMail(mailInfo);
	}

	/**
	 * 发送邮件
	 * 
	 * @param subject 主题
	 * @param content 发件内容
	 * @param toAddressList 多人邮件接收地址
	 * @return
	 */
	public static boolean sendEmail(String subject, String content, List<String> toAddressList,
			List<String> toCCAddressList) {

		MailInfo mailInfo = new MailInfo();
		try {
			subject = MimeUtility.encodeWord(subject, "UTF-8", "Q");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage());
		}
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		mailInfo.setToAddress(StringUtils.join(toAddressList, ","));
		if (!CollectionUtils.isEmpty(toCCAddressList)) {
			mailInfo.setToCCAddress(StringUtils.join(toCCAddressList, ","));
		}
		return sendTextMail(mailInfo);
	}

	/**
	 * 发送单人带附件的邮件
	 * 
	 * @param subject 主题
	 * @param content 邮件内容
	 * @param toAddress 邮件接收地址
	 * @param fis 单附件
	 * @param attachFileName 附件名
	 * @return
	 */
	public static boolean sendEmail(String subject, String content, String toAddress,
			FileInputStream fis, String attachFileName) {

		MailInfo mailInfo = new MailInfo();
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		mailInfo.setToAddress(toAddress);

		// 如果带附件
		if (fis != null) {

			mailInfo.setValidateSSL(true);
			mailInfo.setMailServerPort("465");
			// 设置文件输入流
			List<FileInputStream> attachInputStreams = new ArrayList<FileInputStream>();
			attachInputStreams.add(fis);
			mailInfo.setAttachInputStreams(attachInputStreams);

			// 设置文件名
			List<String> attachFileNames = new ArrayList<String>();
			attachFileNames.add(attachFileName);
			mailInfo.setAttachFileNames(attachFileNames);
		}
		return sendTextMail(mailInfo);
	}

	/**
	 * 发送多人单附件邮件
	 * 
	 * @param subject 主题
	 * @param content 内容
	 * @param toAddressList 多人邮件接收地址
	 * @param fis 单文件附件
	 * @param attachFileName 附件名
	 * @return
	 */
	public static boolean sendEmail(String subject, String content, List<String> toAddressList,
			FileInputStream fis, String attachFileName) {

		MailInfo mailInfo = new MailInfo();
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		mailInfo.setToAddress(StringUtils.join(toAddressList, ","));

		// 如果带附件
		if (fis != null) {

			// 设置文件输入流
			List<FileInputStream> attachInputStreams = new ArrayList<FileInputStream>();
			attachInputStreams.add(fis);
			mailInfo.setAttachInputStreams(attachInputStreams);
			// 设置文件名
			List<String> attachFileNames = new ArrayList<String>();
			attachFileNames.add(attachFileName);
			mailInfo.setAttachFileNames(attachFileNames);
		}
		return sendTextMail(mailInfo);
	}

	/**
	 * 发送单人多附件邮件
	 * 
	 * @param subject 主题
	 * @param content 内容
	 * @param toAddress 单人邮件接收地址
	 * @param fisList 多文件附件
	 * @param attachFileNames 多附件名
	 * @return
	 */
	public static boolean sendEmail(String subject, String content, String toAddress,
			List<FileInputStream> fisList, List<String> attachFileNames) {

		MailInfo mailInfo = new MailInfo();
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		mailInfo.setToAddress(toAddress);

		// 如果带附件
		if (fisList != null && attachFileNames != null) {
			// 如果文件流和文件名的记录不相同
			if (fisList.size() != attachFileNames.size()) {
				return false;
			}
			mailInfo.setAttachInputStreams(fisList);
			mailInfo.setAttachFileNames(attachFileNames);
		}

		return sendTextMail(mailInfo);
	}

	/**
	 * 发送多人多附件邮件
	 * 
	 * @param subject 主题
	 * @param content 内容
	 * @param toAddressList 多人邮件接收地址
	 * @param fisList 多文件附件
	 * @param attachFileNames 多附件名
	 * @return
	 */
	public static boolean sendEmail(String subject, String content, List<String> toAddressList,
			List<FileInputStream> fisList, List<String> attachFileNames) {

		MailInfo mailInfo = new MailInfo();
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		mailInfo.setToAddress(StringUtils.join(toAddressList, ","));

		// 如果带附件
		if (fisList != null && attachFileNames != null) {
			// 如果文件流和文件名的记录不相同
			if (fisList.size() != attachFileNames.size()) {
				return false;
			}
			mailInfo.setAttachInputStreams(fisList);
			mailInfo.setAttachFileNames(attachFileNames);
		}

		return sendTextMail(mailInfo);
	}

	/**
	 * 以文本格式发送邮件
	 * 
	 * @param mailInfo 待发送的邮件的信息
	 * @throws Exception
	 */
	private static boolean sendTextMail(MailInfo mailInfo) {
		// 判断是否需要身份认证
		MailAuthenticator authenticator = null;

		mailInfo.setMailServerHost(mailServerHost);
		mailInfo.setMailServerPort(mailServerPort);
		mailInfo.setFromAddress(fromAddress);
		mailInfo.setUserName(userName);
		mailInfo.setNickName(nickName);
		mailInfo.setPassword(password);
		mailInfo.setValidate(validate);
		mailInfo.setValidateSSL(validateSSL);

		Properties pro = mailInfo.getProperties();

		// 如果需要身份认证
		if (mailInfo.isValidate()) {
			authenticator = new MailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}

		// 如果需要ssl支持
		if (mailInfo.isValidateSSL()) {
			pro.put("mail.smtp.starttls.enable", "true");
			pro.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		}

		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		try {

			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);

			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());

			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			new InternetAddress();

			// 创建邮件的接收者地址，并设置到邮件消息中
			if (StringUtils.isNotEmpty(mailInfo.getToAddress())) {
				InternetAddress[] iaToList = InternetAddress.parse(mailInfo.getToAddress());
				mailMessage.setRecipients(Message.RecipientType.TO, iaToList);
			}

			// 创建邮件的接收者地址，并设置到邮件消息中
			if (StringUtils.isNotEmpty(mailInfo.getToCCAddress())) {
				InternetAddress[] iaToCCList = InternetAddress.parse(mailInfo.getToCCAddress());
				mailMessage.setRecipients(Message.RecipientType.CC, iaToCCList);
			}
			
			// 创建邮件的接收者地址，并设置到邮件消息中
			if(StringUtils.isNotEmpty(mailInfo.getToBCCAddress())){
				InternetAddress[] iaToBCCList = InternetAddress.parse(mailInfo.getToBCCAddress());
				mailMessage.setRecipients(Message.RecipientType.BCC, iaToBCCList);
			}

			if (StringUtils.isNotEmpty(mailInfo.getNickName())) {
				try {
					mailMessage.setFrom(new InternetAddress(mailInfo.getFromAddress(),
							MimeUtility.encodeText(mailInfo.getNickName())));
				} catch (UnsupportedEncodingException e) {
					LOGGER.error(e.getMessage());
				}
			}
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// 设置邮件消息的主要内容
			String mailContent = mailInfo.getContent();

			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();

			// DataSource dataSource = new
			// ByteArrayDataSource(null,contentType);
			// 添加邮件正文
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(mailContent, contentType);
			multipart.addBodyPart(contentPart);

			// 添加附件
			if (mailInfo.getAttachInputStreams() != null) {
				List<FileInputStream> fisList = mailInfo.getAttachInputStreams();
				int index = 0;
				for (FileInputStream fis : fisList) {
					BodyPart attachmentBodyPart = new MimeBodyPart();
					DataSource source;
					try {
						source = new ByteArrayDataSource(fis, contentType);
						attachmentBodyPart.setDataHandler(new DataHandler(source));
						attachmentBodyPart.setFileName(mailInfo.getAttachFileNames().get(index));
						multipart.addBodyPart(attachmentBodyPart);
					} catch (IOException e) {
						e.printStackTrace();
					}
					index++;
				}
			}

			mailMessage.setContent(multipart);
			mailMessage.saveChanges();

			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static String getMailServerHost() {
		return mailServerHost;
	}

	public static void setMailServerHost(String mailServerHost) {
		MailUtil.mailServerHost = mailServerHost;
	}

	public static String getMailServerPort() {
		return mailServerPort;
	}

	public static void setMailServerPort(String mailServerPort) {
		MailUtil.mailServerPort = mailServerPort;
	}

	public static String getFromAddress() {
		return fromAddress;
	}

	public static void setFromAddress(String fromAddress) {
		MailUtil.fromAddress = fromAddress;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		MailUtil.userName = userName;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		MailUtil.password = password;
	}

	public static boolean isValidate() {
		return validate;
	}

	public static void setValidate(boolean validate) {
		MailUtil.validate = validate;
	}

	public static boolean isValidateSSL() {
		return validateSSL;
	}

	public static void setValidateSSL(boolean validateSSL) {
		MailUtil.validateSSL = validateSSL;
	}

	public static String getContentType() {
		return contentType;
	}

	public static void setContentType(String contentType) {
		MailUtil.contentType = contentType;
	}

	public static String getNickName() {
		return nickName;
	}

	public static void setNickName(String nickName) {
		MailUtil.nickName = nickName;
	}
}
