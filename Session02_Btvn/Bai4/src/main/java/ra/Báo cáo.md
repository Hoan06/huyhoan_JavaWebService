Phần 2 - Báo cáo kiến trúc đề xuất
Sau khi phân tích, tôi đưa ra khuyến nghị phân tách công nghệ tích hợp rõ ràng cho hai mảng hệ thống như sau:

Phân hệ Core Banking cũ (Legacy System): Đề xuất sử dụng SOAP
   Lý do chọn lựa:

Bảo mật tuyệt đối ở tầng thông điệp: Các giao dịch cốt lõi của ngân hàng yêu cầu dữ liệu phải được mã hóa và xác thực không thể chối cãi (Non-repudiation) qua WS-Security từ điểm đầu đến điểm cuối.

Đảm bảo tính nhất quán giao dịch (ACID): Tính năng WS-AtomicTransaction của SOAP là bắt buộc để kiểm soát các giao dịch phân tán phức tạp, ngăn chặn hoàn toàn rủi ro sai lệch số dư tài khoản khi có sự cố mạng giữa các phân hệ core.

Sự tương thích vốn có: Hệ thống Legacy truyền thống thường được xây dựng trên nền tảng Enterprise (Java EE, .NET cổ điển), vốn đã tích hợp và hoạt động cực kỳ ổn định với giao thức SOAP từ nhiều năm qua.

Phân hệ Web/Mobile mới (Microservices): Đề xuất sử dụng REST
   Lý do chọn lựa:

Tối ưu trải nghiệm người dùng (UX): Định dạng JSON siêu nhẹ giúp ứng dụng di động chạy mượt mà ngay cả trong điều kiện mạng di động (3G/4G/5G) kém ổn định, tiết kiệm băng thông cho khách hàng.

Khả năng mở rộng vô hạn (Scalability): Tính chất Stateless của REST cho phép hệ thống Microservices dễ dàng chịu tải hàng triệu lượt truy cập vào các dịp cao điểm (như ngày lễ, đợt khuyến mãi) bằng cách tự động tăng số lượng instance (Scale-out).

Tăng tốc độ đổi mới sáng tạo: Giúp đội ngũ Frontend, Mobile và các bên thứ ba (Fintech đối tác) tích hợp nhanh chóng thông qua các tài liệu API trực quan (Swagger/OpenAPI), rút ngắn chu kỳ phát triển tính năng mới từ vài tháng xuống vài ngày.

Mô hình kết nối tổng thể
   Để hai hệ thống này vận hành trơn tru mà không vi phạm quy tắc của nhau, chúng ta sẽ áp dụng mô hình API Gateway làm cầu nối:

Các Microservices giao tiếp nội bộ và giao tiếp ra ngoài Client bằng REST/JSON.

Khi một Microservice cần truy vấn hoặc thực hiện giao dịch ghi sổ vào Core Banking, nó sẽ gửi một request REST tới một dịch vụ trung gian chuyên trách (Integration Layer). Dịch vụ này đóng vai trò chuyển đổi giao thức (Protocol Mediation): dịch từ REST/JSON sang SOAP/XML để làm việc với Core Banking và ngược lại.

Thuật ngữ cốt lõi (Glossary)
Để đảm bảo sự hiểu biết đồng nhất trong toàn bộ tổ chức, dưới đây là định nghĩa các khái niệm kỹ thuật cốt lõi:

SOAP (Simple Object Access Protocol): Một giao thức (Protocol) dựa trên nền tảng XML để trao đổi thông tin giữa các hệ thống máy tính. SOAP có tính quy chuẩn cao, nghiêm ngặt về mặt cấu trúc và độc lập với giao thức truyền tải bên dưới (có thể chạy trên HTTP, SMTP, JMS...).

REST (Representational State Transfer): Một kiểu kiến trúc (Architectural Style) định hướng tài nguyên, hoạt động dựa trên các nguyên lý thiết kế hệ thống phân tán. REST tận dụng trực tiếp các đặc tính sẵn có của giao thức HTTP để truyền tải dữ liệu.

WSDL (Web Services Description Language): File văn bản định dạng XML được sử dụng trong SOAP để mô tả chi tiết cách thức giao tiếp với Web Service, bao gồm các phương thức có sẵn, tham số truyền vào và kiểu dữ liệu trả về. Được coi như một hợp đồng ràng buộc giữa bên cung cấp và bên sử dụng.

Idempotent (Tính lũy đẳng): Đặc tính của một phương thức/tác vụ, trong đó việc thực hiện yêu cầu đó một lần hay nhiều lần liên tiếp với cùng một dữ liệu đầu vào đều mang lại một kết quả duy nhất và trạng thái hệ thống không thay đổi từ lần gọi thứ hai. (Ví dụ trong REST: GET, PUT, DELETE có tính lũy đẳng; POST thì không).

Stateless (Không lưu trạng thái): Nguyên lý thiết kế yêu cầu mỗi request từ Client gửi lên Server phải chứa đầy đủ thông tin cần thiết để Server hiểu và xử lý độc lập. Server không lưu giữ bất kỳ ngữ cảnh hay trạng thái nào của Client (Session) giữa các lần gọi liên tiếp, giúp tối ưu hiệu suất và dễ dàng mở rộng hệ thống.

WS-Security (Web Services Security): Một chuẩn mở rộng của SOAP cung cấp các cơ chế bảo mật toàn diện ở tầng thông điệp, bao gồm việc áp dụng mã hóa dữ liệu (Encryption) và ký số xác thực (Digital Signature) trực tiếp trên các phần tử XML để chống giả mạo và nghe lén.

Content Negotiation (Thương lượng nội dung): Cơ chế trong HTTP cho phép Client và Server thỏa thuận về định dạng dữ liệu trả về (Ví dụ: JSON hoặc XML) thông qua các thông số nằm trên HTTP Header (như Accept hoặc Content-Type).