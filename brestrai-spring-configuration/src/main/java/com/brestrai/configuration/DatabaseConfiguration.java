package com.brestrai.configuration;

import com.brestrai.utils.template.configurations.database.JpaConfiguration;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConfiguration extends JpaConfiguration {

    @Override
    public String setEntityScanPackage() {
        return null;
    }

    @Override
    public String setDriver() {
        return null;
    }

    @Override
    public String setUrl() {
        return null;
    }

    @Override
    public String setUsername() {
        return null;
    }

    @Override
    public String setPassword() {
        return null;
    }
}
