Đề xuất giải pháp: Chuyển đổi sang sử dụng cơ chế JSON Web Token (JWT)
Đối với một Blockchain Explorer phục vụ hàng triệu người dùng toàn cầu, JWT là giải pháp tối ưu và bắt buộc. Lợi ích về mặt Scalability và khả năng tích hợp không trạng thái (Stateless) hoàn toàn vượt trội so với rủi ro về độ phức tạp triển khai.

Lý do lựa chọn:
Đáp ứng tải phân tán: Hệ thống không bị phụ thuộc vào một cụm database lưu trữ session tập trung. Các microservices chịu trách nhiệm đẩy cảnh báo ví (Alerts) hay tracking giao dịch có thể tự validate token tại chỗ, giúp giảm tải tối đa cho database chính.

Đồng bộ đa nền tảng: Token định dạng chuỗi giúp đồng bộ trải nghiệm theo dõi ví của người dùng mượt mà từ giao diện Web sang ứng dụng Mobile.

Phương án giảm thiểu nhược điểm của JWT:
Để xử lý vấn đề thu hồi quyền hạn và bảo mật, hệ thống sẽ áp dụng mô hình Dual-Token:

Access Token: Có thời gian sống rất ngắn (15 - 30 phút) để hạn chế rủi ro nếu bị lộ.

Refresh Token: Có thời gian sống dài hơn (7 - 30 ngày), được lưu cấu hình nghiêm ngặt tại cơ sở dữ liệu phân tán (Redis) để quản lý phiên, cho phép Server chủ động block hoặc ép người dùng đăng nhập lại khi cần thiết mà không ảnh hưởng tới hiệu năng tổng thể.