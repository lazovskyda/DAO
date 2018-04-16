package com.lazovsky.DAO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
//@Configuration
@ImportResource("transactionContext.xml")
public class DaoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DaoApplication.class, args);
        TestClass testObject = (TestClass) context.getBean("testClass");
		PostgresDAO connection = (PostgresDAO) context.getBean("postgresDAO");
		MP3 myMP3 = (MP3) context.getBean("mp3");

		List<MP3> myList = new ArrayList<MP3>();



		Author authorInit = (Author) context.getBean("author");
		authorInit.setName("ka30!!!!!!!!!!");


		myMP3.setName("zoombe!!!!!!!");
		myMP3.setAuthor(authorInit);


		connection.insertMP3(myMP3);
		//connection.delete(myMP3);

		//connection.getById(4);
		//Object resultMP3 = new Object();

//		List<MP3> resultMP3 = connection.getByName("Second node name");
		List<MP3> resultMP3 = connection.getMP3ListByAuthor("zemfira");

/*		if(resultMP3  instanceof MP3) {
			System.out.println(resultMP3.getName());
			System.out.println(resultMP3.getId());
			System.out.println(resultMP3.getAuthor().getId());
			System.out.println(resultMP3.getAuthor().getName());
		}*/


//		if(resultMP3 instanceof List){
//			resultMP3.forEach(
//					(mp3)->{
//						System.out.println("track------------------");
//						System.out.println(mp3.getName());
//						System.out.println(mp3.getId());
//						System.out.println(mp3.getAuthor().getId());
//						System.out.println(mp3.getAuthor().getName());
//					}
//			);
//		}



//
//		List<MP3> testListIsert = new ArrayList<MP3>();
//		MP3 mp3_list1 = (MP3) context.getBean("mp3");
//		MP3 mp3_list2 = (MP3) context.getBean("mp3");
//
//		Author testAuthor1 = (Author) context.getBean("author");
//		Author testAuthor2 = (Author) context.getBean("author");
//
//		testAuthor1.setName("TestAuthor1");
//		testAuthor2.setName("TestAuthor2");
//
//		mp3_list1.setName("listTest_TrackName1");
//		mp3_list2.setName("listTest_TrackName2");
//		mp3_list1.setAuthor(testAuthor1);
//		mp3_list2.setAuthor(testAuthor2);
//
//		testListIsert.add(mp3_list1);
//		testListIsert.add(mp3_list2);
//
//		connection.batchInsert(testListIsert);








/*


		myMP3.setName("Fuck yeah2222");
		myMP3.setAuthor("ka303030222");

		myMP3_2.setName("Second node name");
		myMP3_2.setAuthor("Second author name");


		myList.add(myMP3);
		myList.add(myMP3_2);


        testObject.test();

        //connection.setDataSource("");
		connection.insert(myMP3_2);

		connection.batchInsert(myList);


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
*/


	}
}
