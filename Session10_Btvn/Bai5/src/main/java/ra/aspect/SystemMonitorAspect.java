package ra.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class SystemMonitorAspect {

    @AfterThrowing(pointcut = "execution(* ra.service..*.*(..))", throwing = "exception")
    public void logSystemFailure(JoinPoint joinPoint, Throwable exception) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        String requestId = MDC.get("requestId");


        log.error("[{}] - [{}.{}] - Error: [{}]", requestId, className, methodName, exception.getMessage());
    }
}