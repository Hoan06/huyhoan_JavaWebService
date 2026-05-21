 PHÂN TÍCH & SO SÁNH CHIẾN LƯỢC XỬ LÝ NGOẠI LỆ

 Bảng so sánh giữa @RestControllerAdvice và @AfterThrowing

| Tiêu chí | @RestControllerAdvice (Spring Web) | @AfterThrowing (Spring AOP) |
| :--- | :--- | :--- |
| **Bản chất công cụ** | Là một giải pháp chuyên biệt của tầng Web (Spring MVC Interrupt), đánh chặn ngoại lệ ngay trước khi phản hồi được gửi về Client. | **Aspect-Oriented Programming (AOP):** Đánh chặn ở mức proxy của mọi phương thức trong hệ thống (Service, Repository, v.v.). |
| **Phạm vi hoạt động** | **Tầng Controller (Web Layer).** Chỉ bắt được các ngoại lệ bay ra khỏi tầng Controller và chuẩn bị đi ra ngoài API. | **Mọi tầng (Bất cứ nơi nào cấu hình Pointcut).** Thường dùng nhiều nhất ở Tầng Service để bắt các lỗi nghiệp vụ nội bộ. |
| **Khả năng đổi cấu trúc JSON** | **Tuyệt đối mạnh mẽ.** Cho phép định nghĩa lại hoàn toàn Object trả về (gắn HTTP Status, Custom JSON) nhờ tích hợp sâu với HttpMessageConverter. | **Không thể.** Nó chỉ là một cơ chế "nghe lén" khi hàm ném lỗi, lỗi sau đó vẫn tiếp tục bay tiếp lên tầng trên chứ không thể can thiệp thay đổi cấu trúc HTTP Response. |
| **Khả năng ghi Log ngầm** | **Tốt ở mức bề nổi.** Ghi được thông tin Exception, nhưng khó lấy được chi tiết ngữ cảnh tham số truyền vào hàm Service (vì nó nằm ở tầng Controller). | **Cực kỳ mạnh mẽ & Chi tiết.** Nhờ có `JoinPoint`, nó lấy được chính xác hàm Service nào bị lỗi, các tham số đầu vào (Arguments) truyền vào là gì để phục vụ Debug. |

Quyết định lựa chọn kiến trúc
HTTP Response (Tạo JSON lỗi 404 chuẩn):** Bắt buộc chọn **`@RestControllerAdvice`**. Vì đây là công cụ duy nhất có thể can thiệp vào luồng HTTP, giấu StackTrace và đóng gói dữ liệu thành JSON theo đúng chuẩn Frontend yêu cầu.
Audit log (Ghi log bảo mật/Debug nội bộ):** Lựa chọn tối ưu là **`@AfterThrowing`**. Nó sẽ âm thầm ghi lại chi tiết "hiện trường vụ án" (Tên hàm Service, các tham số đầu vào làm lỗi hệ thống) ra file hệ thống mà không hề can thiệp hay làm rác luồng xử lý lỗi của client.