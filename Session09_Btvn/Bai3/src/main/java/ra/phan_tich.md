 Phân tích: Tại sao Parameterized Logging ({}) tối ưu hiệu năng hơn toán tử cộng chuỗi (+)?

Khi viết câu lệnh log sử dụng toán tử cộng chuỗi `+` như sau:
`logger.debug("Bắt đầu cập nhật kho cho SP: " + productId + ", SL: " + qty);`

Hệ điều hành và JVM sẽ phải chịu một tổn thất hiệu năng vô ích vì:
Phải tính toán cộng chuỗi trước khi kiểm tra Level Log: Trình biên dịch Java bắt buộc phải khởi tạo một đối tượng `StringBuilder` (hoặc tối ưu bằng `StringConcatFactory` ở Java đời cao) để nối các chuỗi và biến lại với nhau thành một chuỗi hoàn chỉnh *trước* khi truyền vào hàm `debug()`.
Lãng phí tài nguyên CPU và bộ nhớ Heap: Giả sử trên môi trường Production, chúng ta cấu hình Log level ở mức `INFO` (tức là tắt mức `DEBUG`). Dù dòng log `DEBUG` trên không bao giờ được in ra file hay console, JVM vẫn phải tốn tài nguyên CPU để cộng chuỗi và cấp phát một vùng nhớ tạm thời trên Heap để chứa chuỗi đó, rồi ngay lập tức vứt bỏ đi cho Garbage Collector dọn dẹp.

Ngược lại, khi sử dụng cơ chế Parameterized Logging (`{}`):
`log.debug("Bắt đầu cập nhật kho cho SP: {}, SL: {}", productId, qty);`

 Cơ chế Đánh giá Lười biếng (Lazy Evaluation): Chuỗi gốc chứa các ký tự đại diện `{}` và mảng các tham số được truyền thẳng vào hàm của SLF4J dưới dạng các tham chiếu độc lập. 
 Kiểm tra Level trước, xử lý sau: SLF4J sẽ kiểm tra xem log level hiện tại của hệ thống có cho phép ghi mức `DEBUG` hay không. Nếu KHÔNG (bị tắt), SLF4J lập tức thoát ra khỏi hàm. Quá trình phân tích cú pháp và thay thế biến vào vị trí `{}` **hoàn toàn không diễn ra**. Nhờ đó, hệ thống tiết kiệm được 100% chi phí tính toán và không sinh ra rác bộ nhớ.