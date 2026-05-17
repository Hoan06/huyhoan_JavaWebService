Phần 1 - Phân tích logic chuyên sâu
Để đưa ra quyết định kiến trúc chính xác, chúng ta cần đặt các đặc tính kỹ thuật của SOAP và REST lên bàn cân dựa trên 5 khía cạnh cốt lõi mà doanh nghiệp tài chính yêu cầu:

Hiệu suất (Performance) & Tài nguyên
   SOAP: Sử dụng duy nhất định dạng dữ liệu XML. Cấu trúc tin nhắn SOAP luôn bao gồm các thẻ bắt buộc như <Envelope>, <Header>, và <Body>. Việc đóng gói dữ liệu cồng kềnh này làm tăng kích thước băng thông truyền tải. Đồng thời, CPU của Server và Client phải tốn nhiều tài nguyên hơn để phân tích cú pháp (parse) chuỗi XML phức tạp. Điều này không phù hợp với môi trường Mobile/Web vốn ưu tiên tốc độ tải và tiết kiệm pin, dung lượng mạng.

REST: Cho phép sử dụng JSON - định dạng dữ liệu có cấu trúc cực kỳ tối giản, gọn nhẹ. REST hoạt động trực tiếp dựa trên các phương thức HTTP thuần túy (GET, POST, PUT, DELETE), tận dụng tối đa cơ chế HTTP Caching để giảm tải cho Server. Do đó mang lại tốc độ phản hồi vượt trội, đáp ứng tốt lượng truy cập lớn (high-traffic).

Bảo mật (Security) & Tính toàn vẹn giao dịch
   SOAP: Sở hữu tiêu chuẩn bảo mật cấp doanh nghiệp WS-Security (Web Services Security). Tiêu chuẩn này cho phép mã hóa và ký số trực tiếp trên từng phần của thông điệp XML (Message-level security), bảo vệ dữ liệu toàn vẹn ngay cả khi đi qua các nút trung gian (Proxy, API Gateway) trước khi tới đích. Đặc biệt, SOAP hỗ trợ WS-AtomicTransaction, đảm bảo tính chất ACID (Atomicity, Consistency, Isolation, Durability) cho các giao dịch phân tán trên nhiều hệ thống khác nhau (Ví dụ: Tiền phải trừ ở tài khoản A đồng thời cộng vào tài khoản B, nếu một bên lỗi thì toàn bộ tiến trình tự động rollback).

REST: Phụ thuộc vào giao thức bảo mật tầng truyền tải HTTPS/TLS (Transport-level security). Nếu thông điệp đi qua một proxy trung gian có giải mã TLS, dữ liệu có nguy cơ bị lộ nếu không có biện pháp mã hóa bổ sung ở tầng ứng dụng. REST thường sử dụng Token-based (như JWT, OAuth2) cho việc xác thực, rất linh hoạt nhưng không hỗ trợ sẵn các chuẩn giao dịch phân tán phức tạp như SOAP.

Độ phức tạp (Complexity) & Tính chặt chẽ
   SOAP: Rất nghiêm ngặt và có độ phức tạp cao. SOAP bắt buộc phải có một file giao ước cấu trúc WSDL (Web Services Description Language) để định nghĩa chính xác mọi hàm, kiểu dữ liệu. Client và Server bị ràng buộc chặt chẽ (tightly coupled) bởi file WSDL này; bất kỳ thay đổi nhỏ nào về kiểu dữ liệu cũng yêu cầu biên dịch lại mã nguồn của cả hai bên.

REST: Theo kiến trúc ràng buộc lỏng lẻo (loosely coupled), không áp đặt một giao ước cứng nhắc (mặc dù có thể dùng OpenAPI/Swagger để làm tài liệu). REST mang tính chất Stateless (không lưu trạng thái), giúp đơn giản hóa việc thiết kế, vận hành và bảo trì hệ thống.

Khả năng phát triển (Agility) & Mở rộng (Scalability)
   SOAP: Quy trình phát triển chậm do phải sinh mã (code generation) từ file WSDL, cấu hình bảo mật WS-Security phức tạp. Khó mở rộng theo quy mô chiều ngang (Horizontal Scaling) nếu hệ thống áp dụng các kiến trúc lưu trạng thái (Stateful).

REST: Giúp tăng tốc độ phát triển (Time-to-market) tối đa. Các framework hiện đại (như Spring Boot) hỗ trợ REST cực kỳ mạnh mẽ, giúp lập trình viên viết API chỉ trong vài phút. Do tính chất Stateless, các dịch vụ REST cực kỳ dễ dàng nhân bản, triển khai lên Docker/Kubernetes để mở rộng quy mô linh hoạt theo thời gian thực.