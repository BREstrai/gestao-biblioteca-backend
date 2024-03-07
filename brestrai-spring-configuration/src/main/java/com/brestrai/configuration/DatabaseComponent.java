package com.brestrai.configuration;

import com.brestrai.utils.template.configurations.database.DatabaseConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
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

        return entityScanPackage;
    }

    @Override
    public String getDriver() {

        return driver;
    }

    @Override
    public String getUrl() {

        return url;
    }

    @Override
    public String getUsername() {

        return username;
    }

    @Override
    public String getPassword() {

        return password;
    }

}
