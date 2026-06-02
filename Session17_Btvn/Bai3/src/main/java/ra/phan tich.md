Để thực hiện yêu cầu "chỉ người tạo ra một tác vụ mới được phép xóa tác vụ đó", @PreAuthorize bắt buộc phải được ưu tiên sử dụng thay vì @Secured.

Sự khác biệt: @Secured là annotation đời cũ, cấu hình tĩnh và chỉ hỗ trợ kiểm tra chuỗi vai trò cơ bản (ví dụ: @Secured("ROLE_ADMIN")). Ngược lại, @PreAuthorize hỗ trợ ngôn ngữ biểu thức SpEL (Spring Expression Language) mạnh mẽ.

Lý do lựa chọn: Quy tắc xóa task yêu cầu kiểm tra điều kiện động (so sánh logic giữa thực thể dữ liệu trong cơ sở dữ liệu và người dùng hiện tại đang đăng nhập). @PreAuthorize cho phép truyền trực tiếp tham số của phương thức (như taskId) vào các câu lệnh SpEL hoặc gọi đến một Spring Bean (taskService) để truy vấn dữ liệu trước khi thực thi phương thức, điều mà @Secured hoàn toàn bất lực.