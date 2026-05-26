Phần 1: Phân tích logic - Thách thức kiểm thử trong kiến trúc Microservices
Kiến trúc microservices mang lại khả năng mở rộng cao nhưng lại đẩy độ phức tạp của việc kiểm thử lên một cấp độ hoàn toàn khác so với monolithic.

Các thách thức kiểm thử đặc thù
   Tính phân tán và phụ thuộc dữ liệu: Trong monolithic, một luồng nghiệp vụ gọi qua lại giữa các hàm trong bộ nhớ (In-memory). Trong microservices, BookingService phải gọi REST API sang UserService để xác thực, rồi bắn dữ liệu sang NotificationService. Mỗi service có một Database riêng, dẫn đến việc thiết lập trạng thái dữ liệu (Data State) cho việc test rất khó khăn và tốn chi phí.

Xử lý bất đồng bộ và độ trễ mạng (Network Latency & Flakiness): Mạng máy tính không bao giờ ổn định 100%. Các bài test tích hợp rất dễ bị lỗi "vặt" (flaky tests) do rớt mạng, timeout, hoặc dữ liệu không đồng bộ kịp thời giữa các dịch vụ.

Quản lý phiên bản dịch vụ (Version Coherence): Khi UserService cập nhật một API mới (ví dụ: đổi tên trường dữ liệu), làm sao để biết BookingService sẽ bị sập ngay lập tức nếu không chạy toàn bộ hệ thống lên để kiểm tra?

Tại sao chỉ dựa vào Unit Test là chưa đủ?
   Unit Test sinh ra để kiểm tra tính đúng đắn của thuật toán cô lập (Isolated Logic) trong một Class/Method bằng cách sử dụng Mock.

Unit Test mù quáng trước tích hợp: Unit test của BookingService có thể chạy Passed 100% vì chúng ta đã mock rằng UserService luôn trả về HTTP 200. Nhưng trên thực tế trên Staging, UserService có thể đổi endpoint từ /api/v1/users thành /api/v2/users hoặc cấu hình sai Spring Security khiến cấu hình kết nối thật bị từ chối (HTTP 401/403).

Unit Test không bắt được lỗi cấu hình: Các lỗi cấu hình SQL (sai tên cột, sai kiểu dữ liệu JPA), cấu hình cổng kết nối, cấu hình môi trường... hoàn toàn nằm ngoài tầm kiểm soát của Unit Test.