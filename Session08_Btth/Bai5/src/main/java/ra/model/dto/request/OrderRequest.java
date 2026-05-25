package ra.model.dto.request;

import jakarta.validation.constraints.*;

public class OrderRequest {
    @NotBlank(message = "Mã chứng khoán không được để trống")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Mã chứng khoán phải viết hoa toàn bộ và có đúng 3 ký tự")
    private String stockCode;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 100, message = "Số lượng đặt lệnh tối thiểu là 100")
    private Integer quantity;

    @NotNull(message = "Giá đặt lệnh không được để trống")
    @Positive(message = "Giá đặt lệnh phải lớn hơn 0")
    private Double price;

    @NotBlank(message = "Loại lệnh không được để trống")
    @Pattern(regexp = "^(BUY|SELL)$", message = "Loại lệnh phải là BUY hoặc SELL")
    private String orderType;

    public String getStockCode() { return stockCode; }
    public void setStockCode(String stockCode) { this.stockCode = stockCode; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getOrderType() { return orderType; }
    public void setOrderType(String orderType) { this.orderType = orderType; }

    @AssertTrue(message = "Khối lượng đặt lệnh phải là bội số của 100")
    public boolean isLotSizeValid() {
        return this.quantity != null && this.quantity % 100 == 0;
    }
}