package com.brestrai.configuration;

import com.brestrai.utils.template.configurations.database.JpaConfiguration;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConfiguration extends JpaConfiguration {

    @Override
    public String setEntityScanPackage() {
        return "com.brestrai";
    }

    @Override
    public String setDriver() {
        return "org.postgresql.Driver";
    }

    @Override
    public String setUrl() {
        return "jdbc:postgresql://localhost:5432/brestrai";
    }

    @Override
    public String setUsername() {
        return "postgres";
    }

    @Override
    public String setPassword() {
        return "postgres";
    }

}
