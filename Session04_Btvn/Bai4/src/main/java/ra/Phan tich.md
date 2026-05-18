Phần 1 – Phân tích lỗi từ đoạn code của Junior Dev
Đoạn code của lập trình viên Junior:

Java
@PostMapping("/products/{productId}")
public Product updateProduct(@PathVariable String productId, @RequestBody Product p) { ... }
Đoạn khai báo trên có 3 lỗi nghiêm trọng về cả tư duy thiết kế RESTful API lẫn nghiệp vụ hệ thống:

Sai HTTP Method (@PostMapping):

Lỗi: Sử dụng POST cho hành động cập nhật một tài nguyên đã tồn tại dựa trên ID.

Giải thích: Theo chuẩn REST, POST dùng để tạo mới một tài nguyên không xác định trước ID. Khi hành động là cập nhật/thay thế toàn bộ dữ liệu của một tài nguyên đã biết rõ ID ({productId}), phương thức bắt buộc phải là PUT (hoặc PATCH nếu cập nhật một phần).

Dư thừa dữ liệu hoặc Sai vị trí ID trong Body:

Lỗi: Nhận productId trên URL qua @PathVariable, nhưng lại nhận toàn bộ đối tượng Product p từ @RequestBody.

Giải thích: Điều này tạo ra rủi ro xung đột dữ liệu (Data Conflict). Chuyện gì xảy ra nếu Client gửi request đến URL /products/P001 (Cập nhật sản phẩm 1) nhưng trong Request Body JSON lại viết "productId": "P099"? Việc bất đồng nhất giữa ID trên URL và ID trong Body sẽ gây lỗi logic khi lưu xuống Database nếu không được validate chặt chẽ.

Thiếu phản hồi trạng thái HTTP và xử lý ngoại lệ:

Lỗi: Hàm trả về trực tiếp đối tượng Product.

Giải thích: Nếu tìm thấy sản phẩm, việc trả về Product với HTTP Status 200 OK tạm chấp nhận được. Nhưng theo yêu cầu nghiệp vụ: "Nếu không tồn tại -> trả về thông báo lỗi". Khai báo kiểu trả về là Product thuần túy sẽ khiến cấu trúc code rất khó linh hoạt để trả về một chuỗi thông báo lỗi (String) hoặc một mã lỗi tùy chỉnh kèm HTTP Status phù hợp như 404 Not Found.