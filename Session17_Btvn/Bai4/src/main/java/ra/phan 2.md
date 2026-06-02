Hệ thống Blockchain Explorer đặc thù xử lý lượng truy cập rất lớn, phân tán địa lý và tích hợp nhiều nền tảng (Web/Mobile) cùng kiến trúc Microservices. Việc lựa chọn cơ chế xác thực ảnh hưởng trực tiếp đến tính mở rộng (Scalability) và hiệu năng cốt lõi của toàn hệ thống.

Chiến lược Session-based Authentication
Cơ chế: Lưu trạng thái (Stateful) tại RAM/Database của Server, trả Session ID về Client qua Cookie.

Ưu điểm:

Kiểm soát tuyệt đối trạng thái: Server có thể lập tức thu hồi quyền truy cập (Revoke/Logout) của người dùng bất kỳ lúc nào bằng cách xóa Session trong bộ nhớ.

Kích thước nhỏ gọn: Cookie chỉ truyền một chuỗi ID ngắn, giảm băng thông request.

Nhược điểm:

Nghẽn cổ chai về Scalability: Việc lưu trữ hàng triệu phiên làm việc đồng thời ngốn dung lượng RAM khổng lồ. Khi nâng cấp hệ thống (Scale-out), buộc phải cấu hình Sticky Sessions hoặc đồng bộ bộ nhớ tập trung (Redis/Memcached), tạo ra điểm lỗi duy nhất (Single Point of Failure).

Bất lợi trên nền tảng chéo (Cross-device): Các ứng dụng Mobile (iOS/Android) không xử lý Cookie tốt như Web, dễ gặp lỗi và giảm trải nghiệm liền mạch.

Rào cản Microservices: Mỗi dịch vụ nhỏ khi nhận request lại phải truy vấn ngược về database chứa Session để xác thực danh tính, gây trễ (latency) hệ thống.

Chiến lược JSON Web Token (JWT)
   Cơ chế: Không lưu trạng thái (Stateless). Toàn bộ thông tin định danh và quyền hạn được mã hóa, ký số rồi lưu hoàn toàn tại phía Client.

Ưu điểm:

Khả năng mở rộng vô hạn: Server không cần tốn bất kỳ tài nguyên RAM nào để lưu phiên làm việc. Khi lượng người dùng tăng đột biến, hệ thống chỉ cần nhân bản các node backend chạy song song mà không cần đồng bộ dữ liệu phiên.

Tối ưu hóa cho Microservices và Cross-device: Mỗi Microservice có thể tự giải mã token bằng Secret Key để xác thực người dùng độc lập ngay lập tức (Decentralized Verification). Token được truyền qua HTTP Header Authorization: Bearer nên tương thích hoàn hảo với cả Web, iOS, Android, và các API bên thứ ba.

Nhược điểm:

Khó khăn khi thu hồi (Revocation): Vì Stateless, một khi JWT đã được cấp phát, nó sẽ có hiệu lực cho đến khi hết hạn. Việc khóa tài khoản ngay lập tức hoặc ép logout là cực kỳ phức tạp (yêu cầu triển khai thêm cơ chế Blacklist/Whitelist trên Redis).

Kích thước Token lớn: JWT chứa Header, Payload (userId, roles, alerts_config...) và Signature nên nặng hơn nhiều so với Session ID, làm tăng dung lượng tải của mọi HTTP request.