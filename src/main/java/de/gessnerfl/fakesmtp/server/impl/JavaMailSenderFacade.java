package de.gessnerfl.fakesmtp.server.impl;

import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class JavaMailSenderFacade {

  static final String ERROR_MESSAGE = "Spring mail system is not configured; Skip email forwarding";

  private JavaMailSender javaMailSender;
  private Logger logger;

  public void send(MimeMessage mimeMessage) {
    if (javaMailSender != null) {
      logger.debug("mime message: {}", mimeMessage);
      javaMailSender.send(mimeMessage);
    } else {
      logger.error(ERROR_MESSAGE);
    }
  }

  public void send(SimpleMailMessage message) {
    if (javaMailSender != null) {
      logger.debug("simple mail message: {}", message);
      javaMailSender.send(message);
    } else {
      logger.error(ERROR_MESSAGE);
    }
  }

  @Autowired(required = false)
  public void setJavaMailSender(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Autowired
  public void setLogger(Logger logger) {
    this.logger = logger;
  }
}
