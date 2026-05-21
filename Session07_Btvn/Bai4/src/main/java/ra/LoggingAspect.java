package ra;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @AfterThrowing(
            pointcut = "execution(* ra..*(..))",
            throwing = "exception"
    )
    public void logServiceError(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        Object[] args = joinPoint.getArgs();

        System.err.println("=== INTERNAL LOG START ===");
        System.err.println("VỊ TRÍ LỖI: " + className + "." + methodName + "()");
        System.err.println("THAM SỐ ĐẦU VÀO: " + Arrays.toString(args));
        System.err.println("CHI TIẾT MÃ LỖI (StackTrace): ");
        exception.printStackTrace();
        System.err.println("=== INTERNAL LOG END ===");
    }
}