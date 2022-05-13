//package com.hebeu.common;
//
//import org.apache.log4j.Logger;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.springframework.stereotype.Component;
//
///**
// * Created with IntelliJ IDEA.
// *
// * @ClassName: Myadvice
// * @Author: Smoadrareun
// * @Description: TODO
// */
//
///**
// * 增强类 MyAdvice
// */
//@Aspect
//@Component//这个注解不能少
//public class MyAdvice {
//    public Logger log = Logger.getRootLogger();
//    /*
//     * execution(* Demo1.*Impl.*(..)):表示切点 即:需要增强处理的方法
//     * 此处表示Demo1包下面以Impl结尾的任一方法 方法参数为动态参数(其实就是为了匹配到目标方法 这里也可以直接写方法名)
//     * */
//    @Before("execution(* com.hebeu.service.impl.*.*(..))")
//    public void before() {
//        log.info("前置增强");
//    }
//    @AfterReturning("execution(* com.hebeu.service.impl.*.*(..))")
//    public void afterReturn() {
//        log.info("后置增强");
//    }
//    @AfterThrowing("execution(* com.hebeu.service.impl.*.*(..))")
//    public void afterThrowing() {
//        log.info("异常增强");
//    }
//    @After("execution(* com.hebeu.service.impl.*.*(..))")
//    public void after() {
//        log.info("最终增强");
//    }
//    @Around("execution(* com.hebeu.service.impl.*.*(..))")
//    public Object around(ProceedingJoinPoint pj) throws Throwable {
//        log.info("环绕增强前面");
//        Object ob = pj.proceed();//调用切点的方法
//        log.info("环绕增强后面");
//        return ob;
//    }
//}