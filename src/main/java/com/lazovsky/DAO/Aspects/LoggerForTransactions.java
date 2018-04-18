package com.lazovsky.DAO.Aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Aspect
@Component
public class LoggerForTransactions {

    @Pointcut("@annotation(com.lazovsky.DAO.Annotations.TestAnnotation)")
    public void logger(){}

    @Before("logger()")
    public void beforeLogger(){
        System.out.println("HereIsIt");
        System.out.println(TransactionSynchronizationManager.isActualTransactionActive());
    }

}
