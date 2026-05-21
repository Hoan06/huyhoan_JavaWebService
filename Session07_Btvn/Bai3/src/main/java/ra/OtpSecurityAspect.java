package ra;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OtpSecurityAspect {

    @Around("@annotation(ra.RequiresOTP)")
    public Object validateOtp(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        String otpValue = null;
        boolean foundOtpParam = false;

        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                if ("otp".equalsIgnoreCase(parameterNames[i])) {
                    foundOtpParam = true;
                    if (args[i] != null) {
                        otpValue = args[i].toString().trim();
                    }
                    break;
                }
            }
        }

        if (!foundOtpParam) {
            throw new IllegalStateException("Hệ thống lỗi: Phương thức yêu cầu OTP nhưng không tìm thấy tham số 'otp'!");
        }

        if (otpValue == null || otpValue.isEmpty()) {
            throw new IllegalArgumentException("Giao dịch thất bại: Mã OTP không được để trống!");
        }

        if (!"123456".equals(otpValue)) {
            throw new SecurityException("Giao dịch thất bại: Mã OTP nhập vào không chính xác!");
        }

        return joinPoint.proceed();
    }
}
