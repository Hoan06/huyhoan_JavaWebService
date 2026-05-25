package ra.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ra.model.dto.request.OrderRequest;

@Aspect
@Component
@Order(1)
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* ra.service.PlaceOrderService.placeOrder(..)) && args(request, ..)")
    public void logRawOrder(JoinPoint joinPoint, OrderRequest request) {
        log.info("[RAW ORDER REQUEST] Stock: {}, Qty: {}, Price: {}, Type: {}",
                request.getStockCode(), request.getQuantity(), request.getPrice(), request.getOrderType());
    }
}