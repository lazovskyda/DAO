package com.lazovsky.DAO;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;


import java.util.Properties;

@Component("mp3Table")
public class Mp3Table extends DriverManagerDataSource {

    @Override
    @Autowired
    @Value("${spring.datasource.driverClassName}")
    public void setDriverClassName(String driverClassName) {
        super.setDriverClassName(driverClassName);
    }

    @Override
    @Autowired
    @Value("${spring.datasource.url}")
    public void setUrl(String url) {
        super.setUrl(url);
    }


    @Override
    @Autowired
    @Value("${spring.datasource.username}")

    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    @Autowired
    @Value("${spring.datasource.password}")
    public void setPassword(String password) {
        super.setPassword(password);
    }

}
