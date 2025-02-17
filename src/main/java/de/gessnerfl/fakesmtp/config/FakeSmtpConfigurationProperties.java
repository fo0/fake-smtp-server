package de.gessnerfl.fakesmtp.config;

import javax.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.net.InetAddress;

@Component
@ConfigurationProperties(prefix = "fakesmtp")
public class FakeSmtpConfigurationProperties {

    private static final int DEFAULT_PORT = 25;

    @NotNull
    private Integer port = DEFAULT_PORT;
    private InetAddress bindAddress;
    private Authentication authentication;
    private String filteredEmailRegexList;
    private String filteredSubjectRegexList;
    private boolean forwardEmails = false;

    @NotNull
    private Persistence persistence = new Persistence();

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public InetAddress getBindAddress() {
        return bindAddress;
    }

    public void setBindAddress(InetAddress bindAddress) {
        this.bindAddress = bindAddress;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public Persistence getPersistence() {
        return persistence;
    }

    public void setPersistence(Persistence persistence) {
        this.persistence = persistence;
    }

    public String getFilteredEmailRegexList() {
        return filteredEmailRegexList;
    }

    public void setFilteredEmailRegexList(String filteredEmailRegexList) {
        this.filteredEmailRegexList = filteredEmailRegexList;
    }

    public String getFilteredSubjectRegexList() {
        return filteredSubjectRegexList;
    }

    public void setFilteredSubjectRegexList(String filteredSubjectRegexList) {
        this.filteredSubjectRegexList = filteredSubjectRegexList;
    }

    public boolean isForwardEmails() {
        return forwardEmails;
    }

    public void setForwardEmails(boolean forwardEmails) {
        this.forwardEmails = forwardEmails;
    }

    public static class Authentication {
        @NotNull
        private String username;
        @NotNull
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class Persistence {
        static final int DEFAULT_MAX_NUMBER_EMAILS = 100;

        @NotNull
        private Integer maxNumberEmails = DEFAULT_MAX_NUMBER_EMAILS;

        public Integer getMaxNumberEmails() {
            return maxNumberEmails;
        }

        public void setMaxNumberEmails(Integer maxNumberEmails) {
            this.maxNumberEmails = maxNumberEmails;
        }
    }
}
