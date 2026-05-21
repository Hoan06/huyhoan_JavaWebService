package ra.service;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    public String performTransaction(String walletAddress, double amount) {
        System.out.println("BLOCKCHAIN: Đang ghi nhận giao dịch thành công. Đã chuyển " + amount + " BTC tới ví " + walletAddress);

        return "Giao dịch thành công!";
    }
}