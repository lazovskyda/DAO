package com.lazovsky.DAO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DaoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DaoApplication.class, args);
        TestClass testObject = (TestClass) context.getBean("testClass");
		PostgresDAO connection = (PostgresDAO) context.getBean("postgresDAO");
		MP3 myMP3 = (MP3) context.getBean("mp3");

		myMP3.setName("Fuck yeah2222");
		myMP3.setAuthor("ka303030222");


        testObject.test();

        //connection.setDataSource("");
		connection.insert(myMP3);
//		connection.delete(myMP3);

		List<MP3> gettedList = new ArrayList<>();

		gettedList = connection.getMP3ListByAuthor("ka30");


		System.out.println("SEARCHED LIST:");
		gettedList.forEach((mp3)->{

					System.out.print(mp3.getId() +" "+ mp3.getAuthor() +" "+ mp3.getName());
					System.out.println();
				}

		);

		MP3 gettedMp3 = new MP3();

		System.out.println("ONE VALUE");
		gettedMp3 = connection.getById(3);
		System.out.print(gettedMp3.getId() +" "+ gettedMp3.getAuthor() +" "+ gettedMp3.getName());


	}
}
