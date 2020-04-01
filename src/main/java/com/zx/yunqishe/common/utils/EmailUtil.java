package com.zx.yunqishe.common.utils;

import java.util.Properties;
import java.util.concurrent.*;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.zx.yunqishe.common.utils.entity.SendEmail;
import lombok.extern.slf4j.Slf4j;


import com.sun.mail.util.MailSSLSocketFactory;

@Slf4j
public class EmailUtil {
	private SendEmail sendEmail;

	public EmailUtil(SendEmail sendEmail) {
		this.sendEmail = sendEmail;
	}

	private class Run implements Runnable {

        @Override
        public void run() {
            send();
        }
    }

    private class Call implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            return send();
        }
    }

	/**
	 * 发送邮箱验证码
	 * @return
	 */
	private boolean send() {
		//1.创建连接对象javax.mail.Session
		//2.创建邮件对象javax.mail.Message
		//3.发送一封激活邮件

		String from = this.sendEmail.getFrom();
		String host = this.sendEmail.getHost();

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.auth", "true");

		//QQ邮箱需要ssl加密
		MailSSLSocketFactory sf=null;
		try {
			sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			properties.put("mail.smtp.ssl.enable", "true");
			properties.put("mail.smtp.ssl.socketFactory",sf);
			String auth = this.sendEmail.getAuth();
			//1.获取默认session对象，getDefaultInstance会使用共享的session，有防御机制，不使用它
			Session session = Session.getInstance(properties, new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from,auth);
				}
			});

			//2.创建邮件对象
			Message message = new MimeMessage(session);
			//2.1设置发件人
			message.setFrom(new InternetAddress(from));
			//2.2设置收件人
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.sendEmail.getTo()));
			//2.3设置邮件主题
			message.setSubject("云启社操作认证^_^");
			String link = this.sendEmail.getLink();
			boolean isCode = this.sendEmail.getType().equals("code");
			//2.4设置邮件内容
			String content = "<html>"
					+ "<head>"
					+ "</head>"
					+ "<body>"
					+ "<h1>"
					+ (isCode?"验证码：":"这是一封激活邮件，激活请点击以下链接")
					+ "</h1>"
					+ "<br/>"
					+ "<h3>"
					+ (isCode?this.sendEmail.getCode():"<a href="+link+">"+link+"</a>")
					+ "</h3>"
					+ "</body>"
					+ "</html>";
			message.setContent(content, "text/html;charset=UTF-8");
			//3.发送邮件
			Transport.send(message);
			return true;
		} catch (Exception e) {
			log.error("邮件发送失败",e);
			return false;
		}
	}

    /**
     * 同步发送
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws TimeoutException
     */
	public Boolean SyncSend() throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<Boolean> future = new FutureTask<Boolean>(new Call());
	    new Thread(future).start();
	    return future.get(this.sendEmail.getSendTimeout(), TimeUnit.MILLISECONDS);
    }

    /**
     * 异步发送
     */
    public void AsyncSend() {
        new Thread(new Run()).start();
    }
}
