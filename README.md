# Fake SMTP Server
[![Build Status](https://github.com/gessnerfl/fake-smtp-server/workflows/CI%2FCD/badge.svg)](https://github.com/gessnerfl/fake-smtp-server/workflows/CI%2FCD/badge.svg)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=de.gessnerfl.fake-smtp-server&metric=alert_status)](https://sonarcloud.io/dashboard/index/de.gessnerfl.fake-smtp-server)

*Simple SMTP Server which stores all received emails in an in-memory database and renders the emails in a web interface*

# Fork
fork from https://github.com/gessnerfl/fake-smtp-server

# Docker
https://hub.docker.com/r/fo0me/fake-smtp-server

# additional features
- filter mails by matching the subject: variable: filteredSubjectRegexList (same like existing: filteredEmailRegexList)

# docker-compose
```yml
version: "3"
services:
  smtp-relay:
    image: fo0me/fake-smtp-server:latest
    ports:
    - 465:5025
    - 5080:5080
    environment:
    - FAKESMTP_FORWARD_EMAILS=true
    - SPRING_MAIL_HOST=
    - SPRING_MAIL_PORT=
    - SPRING_MAIL_USERNAME=
    - SPRING_MAIL_PASSWORD=
    - SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=true
    - SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLED=false
    - SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_ENABLE=true
    - SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST=*
    - SPRING_MAIL_PROPERTIES_MAIL_DEBUG=true
```

# test via telnet
```bash
telnet <server> <smtp-port>
```

paste following content
```bash
helo localhost
mail from:<test@example.com>
rcpt to:<YOU@example.com>
data
From: test@example.com
TO: YOU@example.com
Subject: test mail from command line

this is a test mail
.
quit
```
