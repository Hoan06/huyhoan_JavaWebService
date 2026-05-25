package ra.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ra.model.dto.request.StockOutRequestDTO;
import ra.model.dto.request.StockRequestDTO;
import ra.model.entity.InventoryLog;
import ra.repository.InventoryLogRepository;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class ProductAspect {
    private final InventoryLogRepository inventoryLogRepository;

    @Before("execution(* ra.service.ProductService.deleteProductById(..)) && args(id, role)")
    public void deleteProductById(Long id, String role) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            throw new SecurityException("Từ chối truy cập: User không có quyền ADMIN để xóa sản phẩm!");
        }
    }

    @AfterReturning(
            pointcut = "execution(* ra.service.ProductService.addProduct(..)) && args(productDTO)"
    )
    public void logStockIn(StockRequestDTO productDTO) {
        InventoryLog log = new InventoryLog();
        log.setTimestamp(LocalDateTime.now());
        log.setUsername("SYSTEM_STOCK_IN");
        log.setAction("STOCK_IN");
        log.setDetail("[" + LocalDateTime.now() + "] - Performed STOCK_IN successfully. " +
                "Quantity changed: " + productDTO.getQuantity() + " for SKU: " + productDTO.getSku());

        inventoryLogRepository.save(log);
    }

    @AfterReturning(
            pointcut = "execution(* ra.service.ProductService.updateProduct(..)) && args(productDTO)"
    )
    public void logStockOut(StockOutRequestDTO productDTO) {
        InventoryLog log = new InventoryLog();
        log.setTimestamp(LocalDateTime.now());
        log.setUsername("SYSTEM_STOCK_OUT");
        log.setAction("STOCK_OUT");
        log.setDetail("[" + LocalDateTime.now() + "] - Performed STOCK_OUT successfully. " +
                "Quantity changed: " + productDTO.getQuantity() + " for SKU: " + productDTO.getSku());

        inventoryLogRepository.save(log);
    }

    @Around("execution(* ra.service.ProductService.getInspect(..))")
    public Object trackPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;
        System.out.println("====== [PERFORMANCE TRACK] ======");
        System.out.println("Phương thức: " + joinPoint.getSignature().getName());
        System.out.println("Thời gian thực thi: " + executionTime + " ms");
        System.out.println("=================================");

        return result;
    }
}
