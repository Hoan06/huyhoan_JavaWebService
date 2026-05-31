package ra.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* ra.controller.BookController.*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("[AOP @Before] Đang gọi method: {} | Tham số đầu vào: {}", methodName, args);
    }

    @AfterReturning(
            pointcut = "execution(* ra.service.BookService.*(..))",
            returning = "result"
    )
    public void logAfterReturningService(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("[AOP @AfterReturning] Method thành công: {} | Kết quả trả về: {}", methodName, result);
    }

    @Around("execution(* ra.controller.BookController.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();

        try {
            return joinPoint.proceed();
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("[AOP @Around] Hoàn thành: {} | Thời gian xử lý: {} ms", methodName, executionTime);
        }
    }
}