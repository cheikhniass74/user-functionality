package com.user.spring.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@Profile("!dev")
public class SmtpMailSender implements MailSender {
	
	private static final Log LOG = LogFactory.getLog(SmtpMailSender.class);
	private JavaMailSender javamailSender;
	
	
	@Autowired
	public SmtpMailSender(JavaMailSender javamailSender) {
		super();
		this.javamailSender = javamailSender;
	}



	@Override
	@Async
	public void send(String to, String subject, String body) throws MessagingException{	
		
		LOG.info("Sending mail from Thread " + Thread.currentThread().getName());
	      LOG.info("Sending email" + to);
	      LOG.info("subject "+subject);
	      LOG.info("Body "+ body);
	      
	     

	      MimeMessage message = javamailSender.createMimeMessage();
	      MimeMessageHelper helper = new MimeMessageHelper(message);
	      helper.setSubject(subject);
	      helper.setTo(to);
	      helper.setText(body);

	      javamailSender.send(message);
	      	
			
		}

}
