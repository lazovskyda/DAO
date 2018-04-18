package com.lazovsky.DAO.Aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerForTransactions {

    @Pointcut("@annotation(com.lazovsky.DAO.Annotations.TestAnnotation)")
    public void logger(){}

    @Before("logger()")
    public void beforeLogger(){
        System.out.println("HereIsIt");
    }

}
