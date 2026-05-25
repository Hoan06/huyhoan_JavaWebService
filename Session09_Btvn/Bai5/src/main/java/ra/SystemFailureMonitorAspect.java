package ra;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class SystemFailureMonitorAspect {

    private static final String REQUEST_ID_KEY = "requestId";

    @AfterThrowing(pointcut = "execution(* ra.OrderService.*(..))", throwing = "exception")
    public void logAndTraceCentralizedError(JoinPoint joinPoint, Throwable exception) {

        String requestId = UUID.randomUUID().toString();
        MDC.put(REQUEST_ID_KEY, requestId);

        try {
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();

            log.error("[AOP CENTRALIZED ERROR] Vết lỗi tại [{} -> {}] - Chi tiết: {}",
                    className, methodName, exception.getMessage(), exception);

        } finally {
            MDC.clear();
        }
    }
}
