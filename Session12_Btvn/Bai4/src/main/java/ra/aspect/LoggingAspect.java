package ra.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* ra.controller.StudentController.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        log.info("AOP [Before] -> Method: {} | Args: {}", methodName, args);
    }

    @AfterThrowing(pointcut = "execution(* ra.service.StudentService.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        log.error("AOP [AfterThrowing] -> Method {} threw exception: {}", methodName, ex.getMessage());
    }

    @Around("execution(* ra.controller.StudentController.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        String methodName = joinPoint.getSignature().getName();
        log.info("AOP [Around] -> Method {} executed in {} ms", methodName, executionTime);
        return result;
    }
}