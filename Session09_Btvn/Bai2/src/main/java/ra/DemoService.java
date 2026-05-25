package ra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

    public void checkAndApplyPromotion(String userId, String code) {

        if ("VIP".equals(code)) {
            logger.info("Áp dụng thành công mã giảm giá cho user: {}", userId);

        } else if ("EXPIRED".equals(code)) {
            logger.warn("Khách hàng {} áp dụng mã giảm giá đã hết hạn: {}", userId, code);

        } else {

            try {
                throw new java.sql.SQLException("Connection timed out to MySQL replica");
            } catch (Exception e) {
                logger.error("Lỗi mất kết nối DB khi check mã: {} cho user: {}. Chi tiết sự cố: ", code, userId, e);
            }
        }
    }
}