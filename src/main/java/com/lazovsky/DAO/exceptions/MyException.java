package com.lazovsky.DAO.exceptions;

import org.springframework.jdbc.BadSqlGrammarException;

import java.sql.SQLException;

public class MyException extends Exception{
    private String mess = "Something wrong with arguments";
    public MyException(String msg){
        super(msg);
        System.out.println(mess);
    }
}
