//package org.example.userservice.cluster;
//
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class DataSourceAspect {
//
//    @Before("execution(* org.example.userservice..*Service.get*(..))")
//    public void setReadDataSourceType() {
//        DataSourceContextHolder.setDataSourceType(DataSourceContextHolder.getRandomReadDataSourceKey());
//    }
//
//    @Before("execution(* org.example.userservice..*Service.save*(..)) || execution(* org.example.userservice..*Service.update*(..))")    public void setWriteDataSourceType() {
//        DataSourceContextHolder.setDataSourceType("WRITE");
//    }
//}
