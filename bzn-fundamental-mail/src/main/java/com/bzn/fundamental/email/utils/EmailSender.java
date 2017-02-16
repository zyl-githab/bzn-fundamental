package com.bzn.fundamental.email.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;

public final class EmailSender {
	// FIXME:提取出公共的服务
	private static final int PORT = 25;
	private static Session session = null;
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static ExecutorService executorService = Executors.newFixedThreadPool(5);

	static {
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		session = Session.getInstance(props);
	}

	public static void asyncSend(final String server, final String title, final String emailSender,
			final String emailSenderPwd, final String[] receiver, final String subject,
			final String mailContent, final File[] attachs, final String mimetype) {
		executorService.submit(new Runnable() {
			public void run() {
				EmailSender.send(server, title, emailSender, emailSenderPwd, receiver, subject,
						mailContent, attachs, mimetype);
			}
		});
	}

	/**
	 * 发送邮件
	 * 
	 * @param receivers 收件人
	 * @param subject 标题
	 * @param mailContent 邮件内容
	 * @param attachements 附件
	 * @param mimetype 内容类型 默认为text/plain,如果要发送HTML内容,应设置为text/html
	 */
	public static void send(String server, String title, String emailSender, String emailSenderPwd,
			String[] receivers, String subject, String mailContent, File[] attachements,
			String mimetype) {
		try {
			MimeMessage mimeMessage = new MimeMessage(session);

			String from = MimeUtility.encodeText(title) + "<%s>";

			from = String.format(from, emailSender);

			mimeMessage.setFrom(new InternetAddress(from)); // 显示时：发件人邮件

			InternetAddress[] toAddress = new InternetAddress[receivers.length];
			for (int i = 0; i < receivers.length; i++) {
				toAddress[i] = new InternetAddress(receivers[i]);
			}
			mimeMessage.setRecipients(Message.RecipientType.TO, toAddress);// 收件人邮件
			mimeMessage.setSubject(subject);
			mimeMessage.setReplyTo(new Address[] { new InternetAddress(emailSender) });
			Multipart multipart = new MimeMultipart();
			// 正文
			MimeBodyPart body = new MimeBodyPart();
			body.setContent(mailContent, (!StringUtils.isEmpty(mimetype) ? mimetype : "text/plain")
					+ ";charset=" + DEFAULT_ENCODING);
			multipart.addBodyPart(body);// 发件内容
			// 附件
			if (attachements != null) {
				for (File attachement : attachements) {
					MimeBodyPart attache = new MimeBodyPart();
					// ByteArrayDataSource bads = new
					// ByteArrayDataSource(byte[],"application/x-any");
					attache.setDataHandler(new DataHandler(new FileDataSource(attachement)));
					String fileName = getFilename(attachement.getName());
					attache.setFileName(MimeUtility.encodeText(fileName, DEFAULT_ENCODING, null));
					multipart.addBodyPart(attache);
				}
			}
			mimeMessage.setContent(multipart);
			mimeMessage.setSentDate(new Date());
			Transport transport = session.getTransport();
			transport.connect(server, PORT, emailSender, emailSenderPwd);
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			transport.close();
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("邮件内容编码格式有误，无法发送邮件！");
		} catch (AddressException ex) {
			throw new RuntimeException("邮件地址有误，无法发送邮件！");
		} catch (MessagingException ex) {
			throw new RuntimeException("邮件发送过程中发生错误，无法发送邮件！");
		}
	}

	private static String getFilename(String path) {
		if (path == null) {
			return null;
		}
		int separatorIndex = path.lastIndexOf("/");
		return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
	}
}