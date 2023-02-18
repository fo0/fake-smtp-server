package de.gessnerfl.fakesmtp.server.impl;

import de.gessnerfl.fakesmtp.repository.EmailRepository;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.subethamail.smtp.helper.SimpleMessageListener;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = IOException.class)
public class MessageListener implements SimpleMessageListener {

  private final EmailFactory emailFactory;
  private final EmailFilter emailFilter;
  private final EmailRepository emailRepository;
  private final MessageForwarder messageForwarder;
  private final Logger logger;

  @Autowired
  public MessageListener(EmailFactory emailFactory, EmailFilter emailFilter,
                         EmailRepository emailRepository, MessageForwarder messageForwarder,
                         Logger logger) {
    this.emailFactory = emailFactory;
    this.emailFilter = emailFilter;
    this.emailRepository = emailRepository;
    this.messageForwarder = messageForwarder;
    this.logger = logger;
  }

  @Override
  public boolean accept(String from, String recipient) {
    return true;
  }

  @Override
  public void deliver(String sender, String recipient, InputStream data) throws IOException {
    logger.info("Received email from {} to {}", sender, recipient);

    var rawData = new RawData(sender, recipient, IOUtils.toByteArray(data));
    String subject = Strings.EMPTY;
    try {
      subject = rawData.toMimeMessage()
                       .getSubject();
    } catch (Exception ignored) {
    }

    if (!emailFilter.ignore(sender, recipient, subject)) {
      var email = emailFactory.convert(rawData);
      emailRepository.save(email);
      messageForwarder.forward(rawData);
    }
  }
}
