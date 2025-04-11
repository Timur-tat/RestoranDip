package com.diplom.restoran.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.diplom.restoran.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("🔹 Лог: Метод вызывается - " + joinPoint.getSignature());
    }

    @After("execution(* com.diplom.restoran.controller.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("🔹 Лог: Метод завершил работу - " + joinPoint.getSignature());
    }

    @AfterReturning(value = "execution(* com.diplom.restoran.controller.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("🔹 Лог: Метод успешно выполнен - " + joinPoint.getSignature());
    }

    @AfterThrowing(value = "execution(* com.diplom.restoran.controller.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        System.out.println("❌ Лог: Метод выбросил исключение - " + joinPoint.getSignature() + " | Ошибка: " + ex.getMessage());
    }
}