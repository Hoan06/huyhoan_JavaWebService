Sự khác biệt về bảo vệ CSRF giữa Web truyền thống và REST API
Cơ chế xác thực của Web truyền thống: Dựa trên Session và Cookie. Khi người dùng đăng nhập thành công, Server tạo ra một Session và trình duyệt sẽ tự động lưu trữ mã định danh trong Cookie. Ở các request tiếp theo, trình duyệt tự động đính kèm Cookie này lên Server để nhận diện phiên làm việc.

Nguy cơ tấn công CSRF trên Web truyền thống: Rất cao. Vì trình duyệt có cơ chế tự động gửi Cookie, các trang web độc hại có thể lợi dụng điều này để ép trình duyệt của người dùng gửi các request giả mạo (như chuyển tiền, đổi mật khẩu) tới ứng dụng Web mà người dùng đã đăng nhập trước đó. Do đó, việc sử dụng CSRF Token để đối chiếu thủ công trên mỗi request POST/PUT/DELETE là bắt buộc.

Cơ chế xác thực của REST API: Dựa trên kiến trúc không trạng thái (Stateless) và mã thông báo (Token-based như JWT). Hệ thống không duy trì Session trên Server và không phụ thuộc vào Cookie của trình duyệt.

Khả năng chống CSRF của REST API: Khách hàng (như ứng dụng di động hoặc ứng dụng đơn trang SPA) phải tự lập trình để đính kèm Token vào HTTP Header (ví dụ: Authorization: Bearer <Token>) trong mỗi request. Các trang web độc hại không thể tự truy cập vào bộ nhớ cục bộ của ứng dụng để lấy Token này, từ đó triệt tiêu nguy cơ bị tấn công CSRF.

Hệ lụy của việc vô hiệu hóa CSRF một cách mù quáng
Mất đi lớp phòng thủ tự động của trình duyệt: Nếu tắt CSRF trên một ứng dụng Web truyền thống, hệ thống sẽ chỉ kiểm tra xem Cookie Session có hợp lệ hay không mà bỏ qua bước xác thực nguồn gốc request.

Tạo điều kiện cho Hacker chiếm quyền thực thi nghiệp vụ: Kẻ tấn công chỉ cần lừa người dùng click vào một đường link hoặc banner quảng cáo có chứa mã độc. Trình duyệt của nạn nhân sẽ tự động gửi request kèm Cookie Session hợp lệ đến hệ thống, giúp hacker thực hiện trót lọt các hành vi phá hoại hoặc chiếm đoạt tài sản mà người dùng không hề hay biết.