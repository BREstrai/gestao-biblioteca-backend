package com.brestrai.configuration;

import org.springframework.beans.factory.annotation.Value;

/*
 * Implementar @Component quando tiver a necessidade de utilizar o banco de dados
 * Se não usar, descomente a linha do autoconfigure no application.properties
 * */
public class DatabaseComponent {

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

    // TODO: Implement DatabaseComponent


}
