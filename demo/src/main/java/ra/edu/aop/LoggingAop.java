package ra.edu.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Order(1)
public class LoggingAop {
    @Before("execution(* ra.edu.service.impl.*.*(..))")
    public void loggingBeforeCallMethod(JoinPoint joinPoint){
        log.info("Phương thức {} được gọi", joinPoint.getSignature().getName());
    }
}
