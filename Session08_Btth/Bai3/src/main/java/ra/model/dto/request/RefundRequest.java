package ra.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RefundRequest {
    @NotBlank(message = "Mã giao dịch không được để trống")
    @Pattern(regexp = "^TXN[0-9]{3,8}$", message = "Mã giao dịch không hợp lệ hoặc chứa ký tự độc hại")
    private String transactionCode;

    @NotNull(message = "Số tiền không được để trống")
    @Min(value = 1, message = "Số tiền phải lớn hơn 0")
    private Double amount;

    public String getTransactionCode() { return transactionCode; }
    public void setTransactionCode(String transactionCode) { this.transactionCode = transactionCode; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}