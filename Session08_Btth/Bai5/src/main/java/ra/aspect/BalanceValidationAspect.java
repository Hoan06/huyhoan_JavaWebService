package ra.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ra.exception.InsufficientFundsException;
import ra.model.dto.request.OrderRequest;
import ra.model.entity.AccountBalance;
import ra.repository.AccountBalanceRepository;

@Aspect
@Component
@Order(3)
public class BalanceValidationAspect {

    private final AccountBalanceRepository accountBalanceRepository;

    public BalanceValidationAspect(AccountBalanceRepository accountBalanceRepository) {
        this.accountBalanceRepository = accountBalanceRepository;
    }

    @Before("execution(* ra.service.PlaceOrderService.placeOrder(..)) && args(request, username)")
    public void validateBalance(OrderRequest request, String username) {
        if ("BUY".equalsIgnoreCase(request.getOrderType())) {
            AccountBalance balance = accountBalanceRepository.findById(username)
                    .orElseThrow(() -> new InsufficientFundsException("Tài khoản giao dịch không tồn tại."));

            double totalRequired = request.getQuantity() * request.getPrice();
            if (balance.getCashAvailable() < totalRequired) {
                throw new InsufficientFundsException("Tài khoản không đủ số dư để thực hiện lệnh mua này.");
            }
        }
    }
}