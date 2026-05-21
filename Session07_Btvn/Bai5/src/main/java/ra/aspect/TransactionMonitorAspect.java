package ra.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ra.exception.HighRiskException;
import ra.exception.InvalidAddressException;

@Aspect
@Component
public class TransactionMonitorAspect {

    @Around("execution(* ra..performTransaction(..))")
    public Object monitorAndValidate(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        double amount = 0;
        String walletAddress = "";

        for (int i = 0; i < parameterNames.length; i++) {
            if ("amount".equalsIgnoreCase(parameterNames[i])) {
                amount = (double) args[i];
            } else if ("walletAddress".equalsIgnoreCase(parameterNames[i])) {
                walletAddress = args[i] != null ? args[i].toString().trim() : "";
            }
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Giao dịch thất bại: Số lượng tiền gửi phải lớn hơn 0!");
        }

        if (walletAddress.length() < 5) {
            throw new InvalidAddressException("Giao dịch thất bại: Địa chỉ ví nhận '" + walletAddress + "' không hợp lệ (Phải từ 5 ký tự trở lên)!");
        }

        if (amount > 10000) {
            System.err.println("ALERT: Phát hiện giao dịch vượt ngưỡng an toàn! Số tiền: " + amount + " USD đến ví: " + walletAddress);
            throw new HighRiskException("Giao dịch bị tạm giữ: Giá trị vượt quá 10,000 USD. Yêu cầu phê duyệt thủ công từ Ban Quản Trị!");
        }

        System.out.println("MONITOR: Giao dịch hợp lệ. Đang chuyển luồng vào tầng xử lý cốt lõi...");

        return joinPoint.proceed();
    }
}
