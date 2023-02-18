package de.gessnerfl.fakesmtp.server.impl;

import java.io.IOException;
import java.util.Arrays;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
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

  public void logMimeMessage(MimeMessage message) {
    if (!logger.isDebugEnabled()) {
      return;
    }

    try {
      logger.debug("From: " + Arrays.toString(message.getFrom()));
      logger.debug("To: " + Arrays.toString(message.getRecipients(Message.RecipientType.TO)));
      logger.debug("Subject: " + message.getSubject());
      logger.debug("Sent date: " + message.getSentDate());
      logger.debug("Received date: " + message.getReceivedDate());
      logger.debug("Content type: " + message.getContentType());

      // Get the content of the message
      Object content = message.getContent();
      if (content instanceof Multipart multipart) {
        // If the content is a multipart message, loop through the parts and log each one
        for (int i = 0; i < multipart.getCount(); i++) {
          BodyPart bodyPart = multipart.getBodyPart(i);
          logger.debug("Part " + i + " - Content type: " + bodyPart.getContentType());
          logger.debug("Part " + i + " - Content: " + bodyPart.getContent());
        }
      } else {
        // If the content is not a multipart message, log the content directly
        logger.debug("Content: " + content);
      }
    } catch (MessagingException | IOException e) {
      logger.error("Error logging MimeMessage: " + e.getMessage());
    }
  }

}
