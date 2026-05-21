package ra;

import org.springframework.stereotype.Service;

@Service
public class FintechService {

    @RequiresOTP
    public String withdraw(double amount, String otp) {
        return "Rút tiền thành công " + amount + " USD";
    }

    @RequiresOTP
    public String transfer(String toUser, double amount, String otp) {
        return "Chuyển khoản thành công " + amount + " USD tới " + toUser;
    }

    public String getBalance() {
        return "Số dư hiện tại của bạn là: 5,000 USD";
    }
}