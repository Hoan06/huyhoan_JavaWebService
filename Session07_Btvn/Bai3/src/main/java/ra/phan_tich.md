 PHÂN TÍCH THIẾT KẾ BẢO MẬT ĐỘNG BẰNG CUSTOM ANNOTATION

Định nghĩa Input / Output của hệ thống xác thực OTP
Input (Đầu vào):**
Các tham số nghiệp vụ: Số tiền (`amount`), Người nhận (`toUser`).
Tham số bảo mật: Mã `otp` (Chuỗi String được truyền từ Client).
Output (Đầu ra):**
    Hợp lệ:** Trả về chuỗi thông báo thành công của nghiệp vụ (`"Rút tiền thành công"`, `"Chuyển khoản thành công"`).
    Bất hợp lệ (Sai OTP hoặc OTP trống/null):** Chặn đứng luồng xử lý và ném ra một ngoại lệ `IllegalArgumentException` hoặc `SecurityException` kèm thông điệp lỗi chi tiết.

Tại sao dùng Custom Annotation tối ưu hơn Pointcut theo tên hàm?

| Tiêu chí | Quét theo tên hàm (Name Pattern) | Dùng Custom Annotation (`@RequiresOTP`) |
| :--- | :--- | :--- |
| **Tính tường minh (Explicit)** | **Kém.** Dev đọc code Service không hề biết hàm này đang bị can thiệp ngầm bởi Aspect nào nếu không mở file Aspect ra xem. | **Rất cao.** Nhìn trực tiếp vào hàm thấy ngay `@RequiresOTP`, hiểu luôn điều kiện tiên quyết của hàm. |
| **Độ linh hoạt (Flexibility)** | **Thấp.** Bị trói buộc vào quy tắc đặt tên (ví dụ: phải bắt đầu bằng `withdraw*`, `transfer*`). Nếu dev đặt tên hàm là `sendMoneyToFriend()`, hệ thống sẽ bỏ sót bảo mật ➔ Nguy hiểm. | **Tuyệt đối.** Tên hàm đặt là gì cũng được, chỉ cần đính `@RequiresOTP` lên đầu là lập tức được bảo mật bảo vệ. |
| **Nguy cơ lỗi (Human Error)** | **Cao.** Khi thêm tính năng mới, chỉ cần đặt tên lệch quy tắc một ký tự (ví dụ: `whitdraw` - gõ sai chính tả) là lỗ hổng bảo mật xuất hiện ngay lập tức. | **Thấp.** Trình biên dịch (Compiler) sẽ hỗ trợ gợi ý code, nếu muốn bảo mật thì bắt buộc phải chủ động gắn Annotation vào. |
| **Tách biệt nghiệp vụ** | Quá phụ thuộc vào cấu trúc đặt tên của tầng Business. | Hoàn toàn độc lập, tách biệt (Decoupled). |