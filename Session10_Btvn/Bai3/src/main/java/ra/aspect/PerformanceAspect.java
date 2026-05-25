package ra.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    @Around("execution(* ra.service.impl.InventoryServiceImpl.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();

        try {
            Object result = joinPoint.proceed();

            long duration = System.currentTimeMillis() - startTime;

            if (duration > 500) {
                log.warn("[Performance Alert] Hàm {} quá chậm ({} ms).", methodName, duration);
            } else {
                log.info("Hàm {} chạy mất {} ms.", methodName, duration);
            }

            return result;

        } catch (Throwable throwable) {
            long duration = System.currentTimeMillis() - startTime;
            log.error("[AOP EXCEPTION] Hàm {} xảy ra lỗi sau khi chạy được {} ms. Chi tiết ngoại lệ: ", methodName, duration, throwable);
            throw throwable;
        }
    }
}