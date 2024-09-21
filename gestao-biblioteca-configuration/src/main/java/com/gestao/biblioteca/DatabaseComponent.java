package com.gestao.biblioteca;

import com.brestrai.utils.configurations.database.sql.DatabaseConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseComponent extends DatabaseConfiguration {

    @Value("${app.datasource.entity-scan-package}")
    private String entityScanPackage;
    @Value("${app.datasource.driver-class-name}")
    private String driver;
    @Value("${app.datasource.url}")
    private String url;
    @Value("${app.datasource.username}")
    private String username;
    @Value("${app.datasource.password}")
    private String password;

    @Override
    public String getEntityScanPackage() {

        return this.entityScanPackage;
    }

    @Override
    public String getDriver() {

        return this.driver;
    }

    @Override
    public String getUrl() {

        return this.url;
    }

    @Override
    public String getUsername() {

        return this.username;
    }

    @Override
    public String getPassword() {

        return this.password;
    }
}
