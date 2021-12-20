package de.gessnerfl.fakesmtp.server.impl;

import de.gessnerfl.fakesmtp.config.FakeSmtpConfigurationProperties;
import java.util.Arrays;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EmailFilter {

  private final FakeSmtpConfigurationProperties fakeSmtpConfigurationProperties;
  private final Logger logger;

  @Autowired
  public EmailFilter(FakeSmtpConfigurationProperties fakeSmtpConfigurationProperties, Logger logger) {
    this.fakeSmtpConfigurationProperties = fakeSmtpConfigurationProperties;
    this.logger = logger;
  }

  public boolean ignore(String sender, String recipient, String subject) {
    if(StringUtils.hasText(this.fakeSmtpConfigurationProperties.getFilteredEmailRegexList())){
      return ignoreParticipant(sender) || ignoreParticipant(recipient);
    }

    if(StringUtils.hasText(this.fakeSmtpConfigurationProperties.getFilteredSubjectRegexList())){
      return ignoreSubject(subject);
    }

    return false;
  }

  private boolean ignoreParticipant(String participant) {
    if(StringUtils.hasText(participant)){
      try{
        if(Arrays.stream(this.fakeSmtpConfigurationProperties.getFilteredEmailRegexList().split(",")).anyMatch(participant::matches)){
          logger.info("Participant '{}' matches a filtered email regex entry. Email will be filtered.", participant);
          return true;
        }
      }catch(RuntimeException e){
        logger.error("Unable to check participant '{}' against configured email filteredEmailRegexList '{}'", participant, this.fakeSmtpConfigurationProperties.getFilteredEmailRegexList(), e);
      }
    }
    return false;
  }

  private boolean ignoreSubject(String subject) {
    logger.debug("incoming subject: {} | matcher: {}", subject, this.fakeSmtpConfigurationProperties.getFilteredSubjectRegexList());
    if(StringUtils.hasText(subject)){
      try{
        if(Arrays.stream(this.fakeSmtpConfigurationProperties.getFilteredSubjectRegexList().split(",")).anyMatch(subject::matches)){
          logger.info("subject '{}' matches a filtered subject regex entry. Email will be filtered.", subject);
          return true;
        }
      }catch(RuntimeException e){
        logger.error("Unable to check subject '{}' against configured email filteredEmailRegexList '{}'", subject, this.fakeSmtpConfigurationProperties.getFilteredSubjectRegexList(), e);
      }
    }
    return false;
  }

}
