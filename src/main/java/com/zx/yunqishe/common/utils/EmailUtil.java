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

import com.zx.yunqishe.entity.EmailDispose;
import lombok.extern.slf4j.Slf4j;


import com.sun.mail.util.MailSSLSocketFactory;

@Slf4j
public class EmailUtil {
	private EmailDispose emailDispose;

	public EmailUtil(EmailDispose emailDispose) {
		this.emailDispose = emailDispose;
	}

	private class Run implements Runnable {

        @Override
        public void run() {
            send$();
        }
    }

    private class Call implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            return send$();
        }
    }

	/**
	 * 发送邮箱验证码
	 * @return
	 */
	private boolean send$() {
		//1.创建连接对象javax.mail.Session
		//2.创建邮件对象javax.mail.Message
		//3.发送一封激活邮件
		String from = this.emailDispose.getFrom();
		String host = this.emailDispose.getHost();
		String subject = this.emailDispose.getSubject();
		String content = this.emailDispose.getContent();
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
			String auth = this.emailDispose.getAuth();
			//1.获取默认邮件session对象，备注：getDefaultInstance会使用共享的邮件session，有防御机制，不使用它
			Session session = Session.getInstance(properties, new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, auth);
				}
			});
			//2.创建邮件对象
			Message message = new MimeMessage(session);
			//2.1设置发件人
			message.setFrom(new InternetAddress(from));
			//2.2设置收件人
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.emailDispose.getTo()));
			//2.3设置邮件主题
			message.setSubject(subject);
			//2.4设置邮件内容
			message.setContent(content, "text/html;charset=UTF-8");
			//3.发送邮件
			Transport.send(message);
			return true;
		} catch (Exception e) {
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
	public Boolean syncSend() throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<Boolean> future = new FutureTask<Boolean>(new Call());
	    new Thread(future).start();
	    return future.get(this.emailDispose.getTimeOut(), TimeUnit.MILLISECONDS);
    }

    /**
     * 异步发送
     */
    public void asyncSend() {
        new Thread(new Run()).start();
    }

	/**
	 * 发送
	 * @return
	 * @throws Exception
	 */
    public Object send() throws Exception {
    	Byte async = this.emailDispose.getAsync();
    	if (1 == async) {
    		this.asyncSend();
    		return null;
		}
		return this.syncSend();
	}
}
