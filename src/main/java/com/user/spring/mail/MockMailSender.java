package com.user.spring.mail;

import javax.mail.MessagingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class MockMailSender implements MailSender {

	private static final Log LOG = LogFactory.getLog(MockMailSender.class);

	public void send(String to, String subject, String body)
			throws MessagingException {
		LOG.info("Sending mock mail  email" + to);
		LOG.info("subject:  " + subject);
		LOG.info("Body: " + body);

	}

}
