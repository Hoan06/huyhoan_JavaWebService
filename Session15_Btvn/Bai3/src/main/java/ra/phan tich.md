Nguyên nhân gốc rễ
Khi bạn truy cập vào endpoint /products và thấy ngay danh sách sản phẩm mà không cần đăng nhập, nguyên nhân gốc rễ là do ứng dụng thiếu một cơ chế đánh chặn (Interception) và kiểm soát ở tầng Middleware.

Trong một ứng dụng Spring Boot thuần túy (chưa thêm Spring Security):

Luồng xử lý request trực diện: Khi có một HTTP Request gửi đến (ví dụ: GET /products), DispatcherServlet của Spring MVC sẽ lập tức quét qua các HandlerMapping để tìm Controller phù hợp (ProductController). Sau khi tìm thấy, nó sẽ kích hoạt phương thức getAllProducts() và trả dữ liệu thẳng về cho client.

Không có rào chắn bảo mật: Do project chưa khai báo dependency spring-boot-starter-security, ứng dụng hoàn toàn không có sự xuất hiện của DelegatingFilterProxy hay chuỗi các bộ lọc bảo mật (SecurityFilterChain). Hệ thống không hề tồn tại khái niệm "Authentication" (Ai đang truy cập?) hay "Authorization" (Họ có quyền làm gì?). Vì vậy, mọi request đều được coi là hợp lệ và được xử lý công khai.