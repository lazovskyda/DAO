package com.lazovsky.DAO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.main.web-application-type=reactive")
public class PostgresDAOTest {
    @Autowired
    //@Qualifier("postgresDAO")
    PostgresDAO connection;

    @Test
    public void insert() {
        MP3 mp3 = new MP3();
        mp3.setAuthor("Volodia");
        mp3.setName("InsertMethodTestName");

        //ApplicationContext context = SpringApplication.run(DaoApplication.class, args);


        connection.insert(mp3);


        connection.getByName("InsertMethodTestName");
        assertEquals(mp3.getAuthor(), connection.getByName("InsertMethodTestName").getAuthor());
        connection.delete(mp3);

    }
}