package ra;

public class Order {
    private String orderId;
    private String customerName;
    private String address;
    private String productId;
    private int quantity;

    public Order() {
    }

    public Order(String orderId, String customerName, String address, String productId, int quantity) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.address = address;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}