package de.gessnerfl.fakesmtp.server.impl;

import java.util.Arrays;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import org.thymeleaf.util.StringUtils;

class RawData {
    private final String from;
    private final String to;
    private final byte[] content;
    private MimeMessage mimeMessage;

    RawData(String from, String to, byte[] content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getContentAsString() {
        return new String(content, StandardCharsets.UTF_8);
    }

    public InputStream getContentAsStream() {
        return new ByteArrayInputStream(content);
    }

    public MimeMessage toMimeMessage() throws MessagingException {
        return parseMimeMessage();
    }

    private MimeMessage parseMimeMessage() throws MessagingException {
        var s = Session.getDefaultInstance(new Properties());
        final MimeMessage message = new MimeMessage(s, getContentAsStream());
        message.setRecipients(javax.mail.Message.RecipientType.TO, to);
        return message;
    }

    @Override
    public String toString() {
        return "RawData{" +
            "from='" + from + '\'' +
            ", to='" + to + '\'' +
            ", content-length=" + StringUtils.length(content) +
            ", mimeMessage=" + mimeMessage +
            '}';
    }
}
