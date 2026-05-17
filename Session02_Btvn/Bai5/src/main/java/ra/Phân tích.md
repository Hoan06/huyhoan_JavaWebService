Phần 1 - Phân tích logic hệ thống "Thần Đèn"
Thiết kế API Endpoints và Lựa chọn HTTP Methods
   Để hiện thực hóa 3 điều ước một cách sáng tạo và thực tế, chúng ta sẽ định nghĩa chúng thành các tài nguyên (Resources) cụ thể thay vì đặt tên hàm chung chung.

Điều ước 1 (Ước có một công việc mơ ước): Hành vi tạo mới một tài nguyên "Sự nghiệp".

HTTP Method: POST

Endpoint: /api/v1/genie/wishes/career

Điều ước 2 (Ước thay đổi diện mạo/ngoại hình): Hành vi cập nhật (thay thế) trạng thái ngoại hình hiện tại.

HTTP Method: PUT

Endpoint: /api/v1/genie/wishes/appearance

Điều ước 3 (Ước xin một lời khuyên thông thái ngẫu nhiên): Hành vi truy xuất dữ liệu có sẵn từ Thần Đèn mà không làm thay đổi trạng thái tài nguyên của hệ thống.

HTTP Method: GET

Endpoint: /api/v1/genie/wishes/wisdom

API Kiểm tra lịch sử: Hành vi truy xuất danh sách lịch sử điều ước.

HTTP Method: GET

Endpoint: /api/v1/genie/wishes/history

Lựa chọn HTTP Status Codes cho các kịch bản
   Để giao tiếp tinh tế với Client, hệ thống sẽ sử dụng các mã trạng thái chuẩn quốc tế:

201 Created: Trả về khi thực hiện thành công Điều ước 1 (Tạo sự nghiệp).

200 OK: Trả về khi thực hiện thành công Điều ước 2 (Cập nhật ngoại hình) và Điều ước 3 (Lấy lời khuyên).

400 Bad Request: Trả về khi Client gửi dữ liệu rỗng, thiếu thông tin hoặc sai định dạng (Ví dụ: Ước có công việc nhưng để trống tên ngành nghề).

403 Forbidden: Trả về khi vi phạm nghiêm trọng quy tắc nghiệp vụ của Thần Đèn, cụ thể là:

Người dùng cố tình ước điều ước thứ 4 sau khi đã dùng hết 3 lượt.

Người dùng ước những điều phạm quy (Ví dụ: Ước có thật nhiều tiền mặt vô căn cứ, ước "hại người khác").

Phương án lưu trữ Lịch sử điều ước
   Vì yêu cầu hệ thống vận hành gọn nhẹ, lịch sử điều ước (gồm: thời gian, nội dung điều ước, trạng thái THÀNH CÔNG/BỊ TỪ CHỐI, lý do) sẽ được lưu trữ trong một cấu trúc dữ liệu Thread-safe là CopyOnWriteArrayList trong bộ nhớ tạm (In-memory). Mỗi lần Client gọi vào bất kỳ endpoint điều ước nào, hệ thống sẽ ghi nhận lại một bản ghi log tương ứng vào danh sách này trước khi trả phản hồi về cho Client.