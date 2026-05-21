package ra;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    private String currentUserRole = "VIP";

    @Before("execution(* ra..add*(..))")
    public void verifyUser(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();

        if (!"ADMIN".equals(currentUserRole)) {
            System.err.println("SECURITY WARNING: Phát hiện hành vi truy cập trái phép vào hàm [" + methodName + "]");

            throw new SecurityException("Access Denied: Bạn không có quyền thực hiện hành động này. Yêu cầu quyền ADMIN!");
        }

        System.out.println("SECURITY SUCCESS: Xác thực thành công quyền ADMIN cho hàm [" + methodName + "]");
    }
}
