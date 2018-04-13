package com.lazovsky.DAO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;



@RunWith(SpringRunner.class)
@SpringBootTest
public class PostgresDAOTest {



    @Autowired
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
        assertEquals(mp3.getName(), connection.getByName("InsertMethodTestName").getName());
        connection.delete(mp3);

    }


}