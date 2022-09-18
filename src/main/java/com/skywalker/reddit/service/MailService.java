package com.skywalker.reddit.service;

import com.skywalker.reddit.exception.SpringRedditException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.skywalker.reddit.entity.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
	private final JavaMailSender mailSender;
	private final MailContentBuilder mailContentBuilder;

	@Async
	public void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePreparator = mimeMessage ->{
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("springreddit@email.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
			System.out.println("Its Works");
		};
		try {
			System.out.println("Its Works");
			mailSender.send(messagePreparator);
			System.out.println("After send email statement");
			log.info("Activation email sent!!");		
		}catch(Exception e) {
			throw new SpringRedditException("Exception occurred when sending mail to "+ notificationEmail.getRecipient());
		}
	}
}
