package ra.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class SecurityAspect {

    @Before("@annotation(ra.custom_valid.RequireOtp)")
    public void verifyOtp() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new SecurityException("Không thể xác thực ngữ cảnh yêu cầu");
        }

        HttpServletRequest request = attributes.getRequest();
        String otp = request.getHeader("X-OTP");

        if (otp == null || !otp.equals("123456")) {
            throw new SecurityException("Mã OTP không hợp lệ hoặc đã hết hạn");
        }
    }

    @Before("@annotation(ra.custom_valid.RequireManagerApproval)")
    public void verifyManagerRole() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new SecurityException("Không thể xác thực ngữ cảnh yêu cầu");
        }

        HttpServletRequest request = attributes.getRequest();
        String role = request.getHeader("X-Role");

        if (role == null || !role.equalsIgnoreCase("MANAGER")) {
            throw new SecurityException("Từ chối truy cập! Quyền hạn yêu cầu tối thiểu: MANAGER");
        }
    }
}