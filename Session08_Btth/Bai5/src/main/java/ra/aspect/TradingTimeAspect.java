package ra.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ra.exception.MarketClosedException;
import java.time.LocalTime;

@Aspect
@Component
@Order(2)
public class TradingTimeAspect {

    @Before("execution(* ra.service.PlaceOrderService.placeOrder(..))")
    public void checkTradingTime() {
        LocalTime now = LocalTime.now();
        LocalTime marketOpen = LocalTime.of(9, 0);
        LocalTime marketClose = LocalTime.of(15, 0);

        if (now.isBefore(marketOpen) || now.isAfter(marketClose)) {
            throw new MarketClosedException("Hệ thống từ chối lệnh! Sàn chứng khoán chỉ hoạt động từ 09:00 đến 15:00.");
        }
    }
}