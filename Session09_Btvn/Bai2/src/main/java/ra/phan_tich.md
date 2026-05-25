Phân tích: Định nghĩa và phân loại Log Levels cho hệ thống Khuyến mãi

Để hệ thống giám sát (như ELK Stack, Grafana Loki) hoạt động chính xác và không bắn cảnh báo sai lệch (False Alarms), các sự kiện cần được phân loại nghiêm ngặt theo 3 cấp độ sau:

Cấp độ INFO (Thông tin luồng chạy bình thường):
    - Áp dụng cho: Trường hợp mã hợp lệ ("VIP") và áp dụng thành công.
    - Lý do: Đây là hành vi mong muốn của hệ thống, ghi lại để phục vụ thống kê hiệu suất hoặc kiểm toán luồng đi của dữ liệu khi cần.

Cấp độ WARN (Cảnh báo lỗi nghiệp vụ/Lỗi phía người dùng):
    - Áp dụng cho: Trường hợp mã hết hạn ("EXPIRED") hoặc người dùng nhập sai/không tồn tại.
    - Lý do: Hệ thống vẫn chạy hoàn toàn bình thường, không bị sập hay lỗi code. Lỗi xuất phát từ phía dữ liệu đầu vào của khách hàng. Dev/Ops có thể bỏ qua không cần can thiệp xử lý, nhưng cần lưu lại mức WARN để theo dõi tần suất hoặc hỗ trợ CSKH khi họ thắc mắc.

Cấp độ ERROR (Lỗi nghiêm trọng/Lỗi hệ thống sập):
    - Áp dụng cho: Trường hợp đứt kết nối Database, Timeout dịch vụ, NullPointerException,...
    - Lý do: Đây là lỗi kỹ thuật làm nghẽn tiến trình xử lý, hệ thống không thể tự phục hồi. Mức log này bắt buộc phải kích hoạt chuông cảnh báo (Telegram, Slack, PagerDuty) để đội ngũ Kỹ thuật vào ứng cứu và sửa lỗi khẩn cấp ngay lập tức.