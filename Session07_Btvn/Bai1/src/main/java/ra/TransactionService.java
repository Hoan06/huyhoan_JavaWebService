package ra;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    public boolean processPayment(String accountNumber, double amount) {
        System.out.println("SERVICE: Đang xử lý thanh toán cho tài khoản " + accountNumber);

        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return true;
    }
}