BÁO CÁO TÍCH HỢP: MODULE XÁC THỰC VÀ BẢO MẬT API (EMPLOYEE MANAGEMENT SYSTEM)

Phân biệt Access Token và Refresh Token

Để xây dựng một hệ thống bảo mật không trạng thái (Stateless) tối ưu, việc kết hợp giữa Access Token và Refresh Token là giải pháp bắt buộc nhằm cân bằng giữa tính an toàn và trải nghiệm người dùng.

| Tiêu chí | Access Token | Refresh Token |
| :--- | :--- | :--- |
| **Mục đích** | Dùng để xác thực và cấp quyền cho các request gửi tới API nghiệp vụ bảo mật. Chứa thông tin nhân viên và danh sách quyền hạn (Roles). | Dùng duy nhất để xin cấp lại một Access Token mới khi Access Token cũ hết hạn mà không bắt người dùng đăng nhập lại. |
| **Vòng đời** | **Ngắn** (Ví dụ: 15 phút) để giảm thiểu rủi ro nếu bị lộ lọt. | **Dài** (Ví dụ: 7 ngày) nhằm duy trì phiên làm việc lâu dài cho nhân sự. |
| **Lưu trữ phía Client** | Lưu trong **Memory (Biến ứng dụng)** hoặc **SessionStorage** để đảm bảo biến mất khi đóng tab, tránh tấn công XSS. | Lưu trong **HttpOnly Cookie** với các cờ `Secure` và `SameSite=Strict` để chống lại các cuộc tấn công XSS và CSRF. |

---

 Phân tích rủi ro và Cơ chế phòng ngự với cờ `revoked` / `expired`

 Rủi ro khi lộ Token
Bị lộ Access Token:** Kẻ tấn công có thể mạo danh nhân sự để gọi các API nghiệp vụ. Tuy nhiên, do vòng đời ngắn (15 phút), thiệt hại sẽ được giới hạn trong một khoảng thời gian hẹp.
Bị lộ Refresh Token:** Đây là rủi ro nghiêm trọng hơn vì kẻ tấn công có thể liên tục sinh ra các Access Token mới để chiếm quyền truy cập hệ thống lâu dài.

 Giải pháp phòng ngự bằng thiết kế Cơ sở dữ liệu
Bản chất của JWT là không trạng thái (Stateless), server không thể "thu hồi" một chuỗi mã hóa đã phát hành nếu chưa hết hạn tự nhiên. Vì vậy, việc thiết kế bảng `Token` lưu trữ trạng thái với hai cờ `revoked` và `expired` đóng vai trò là lớp phòng ngự cốt lõi:

Cưỡng chế Đăng xuất (Logout):** Khi nhân sự thực hiện đăng xuất, hệ thống áp dụng **Java Stream API** quét qua toàn bộ các token còn hiệu lực của nhân viên đó trong cơ sở dữ liệu và cập nhật `revoked = true` và `expired = true`. Dù Access Token cũ còn thời hạn mã hóa, bộ lọc (Filter) kiểm tra database sẽ lập tức chặn đứng request.
Vô hiệu hóa Refresh Token bị đánh cắp:** Khi phát hiện bất thường hoặc khi người dùng đổi mật khẩu/đăng xuất, hệ thống hủy toàn bộ các Refresh Token hợp lệ bằng cách bật cờ `revoked`. Kẻ tấn công nắm giữ Refresh Token cũ cũng không thể đổi lấy Access Token mới được nữa.
Đảm bảo tính Audit Log:** Việc sử dụng cơ chế Soft Delete (cập nhật cờ trạng thái thay vì xóa cứng dòng dữ liệu) giúp hệ thống giữ lại lịch sử phát hành token, phục vụ cho việc tra cứu, giám sát và điều tra bảo mật (Audit) sau này.