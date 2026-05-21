package ra.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class DoingAspect {
    @Before("execution(* ra.service.*.*(..))")
    public void beforeProcess(JoinPoint joinPoint) {
        System.out.println("Cài đặt các lệnh để xử lí trước khi thực thi hàm" + joinPoint.getSignature().getName());
        System.out.println("Ví dụ : Xác thực người dùng , validate dữ liệu ,...");
    }

    @Around("execution(* ra.service.*.*(..))")
    public Object aroundProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        Object object = joinPoint.proceed();
        Long endTime = System.currentTimeMillis();
        Long totalTime = endTime - startTime;
        System.out.println("Hàm " + joinPoint.getSignature().getName() + " xử lí hết " + totalTime + " ms");
        return object;
    }

    @After("execution(* ra.service.*.*(..))")
    public void afterProcess(JoinPoint joinPoint) {
        System.out.println("Ghi log thực hiện đối với hàm : " + joinPoint.getSignature().getName());
        System.out.println("Log ghi người thực hiện - ngày thực hiện - hành động thực hiện .");
    }
}
