package ra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlashSaleService {

    private static final Logger logger = LoggerFactory.getLogger(FlashSaleService.class);

    public void applyDiscount(String userId, String code) {
        logger.info("Đang xử lý mã giảm giá: {} cho người dùng: {}", code, userId);

        try {

            logger.info("Áp dụng mã giảm giá {} thành công cho user: {}", code, userId);

        } catch (Exception e) {
            logger.error("Thất bại khi áp dụng mã giảm giá {} cho user: {}. Chi tiết lỗi: ", code, userId, e);
        }
    }
}