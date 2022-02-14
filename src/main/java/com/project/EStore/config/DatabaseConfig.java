package com.project.EStore.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(value = "database")
@Getter
@NoArgsConstructor
public class DatabaseConfig {

    private String adminUsername;
    private String adminPassword;

    public DatabaseConfig setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
        return this;
    }

    public DatabaseConfig setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
        return this;
    }
}
