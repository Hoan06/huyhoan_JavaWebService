Phân tích: Sự khác biệt về mục đích cấu hình Log giữa môi trường Dev và Prod

Việc áp dụng cùng một cấu hình log cho tất cả các môi trường là một sai lầm phổ biến. Tùy thuộc vào giai đoạn vận hành, mục đích sử dụng log sẽ thay đổi hoàn toàn:

Môi trường Development (Dev) - Tối ưu hóa cho tốc độ lập trình và Debug:
    - Mục đích: Giúp Lập trình viên theo dõi ngay lập tức hành vi của code, luồng đi của dữ liệu và các giá trị biến trong quá trình phát triển tính năng hoặc sửa lỗi.
    - Đặc điểm cấu hình:
        - Đầu ra: In trực tiếp ra Console để hiển thị ngay trên Terminal của IDE (IntelliJ, Eclipse). Không cần lưu file vì mã nguồn thay đổi liên tục và lập trình viên chỉ quan tâm đến phiên bản chạy hiện tại.
        - Cấp độ (Level): Đặt ở mức DEBUG (hoặc TRACE) để nhìn thấy toàn bộ các câu lệnh SQL sinh ra, các tham số nạp vào và log chi tiết từ các thư viện framework (Spring, Hibernate).
        - Định dạng: Thường tích hợp màu sắc (Highlighting) để dễ dàng phân biệt bằng mắt thường giữa INFO, WARN, và ERROR.

Môi trường Production (Prod) - Tối ưu hóa cho Hiệu năng, Bảo mật và Giám sát dài hạn:
    - Mục đích: Ghi vết lịch sử giao dịch, kiểm toán nghiệp vụ và phục vụ việc điều tra nguyên nhân gốc rễ (Root Cause) khi hệ thống xảy ra sự cố lớn, đồng thời bảo vệ tài nguyên phần cứng.
    - Đặc điểm cấu hình:
        - Đầu ra: Xuất ra File cứng (`RollingFileAppender`) để lưu trữ lâu dài. Tuyệt đối hạn chế in ra Console vì I/O luồng xuất chuẩn của Console trên Linux Server tiêu tốn rất nhiều CPU và làm chậm ứng dụng (nghẽn luồng).
        - Cấp độ (Level): Nâng lên mức INFO trở lên. Tắt hoàn toàn log `DEBUG` để tránh việc ghi hàng triệu dòng log rác (như SQL thô) làm quá tải băng thông ổ cứng và suy giảm hiệu năng hệ thống.
        - Cơ chế an toàn (Log Rotation): Bắt buộc cấu hình tự động cắt file (giới hạn dung lượng, ví dụ: 10MB) và nén thành tệp `.gz` theo ngày. Đồng thời cấu hình xóa tự động log quá hạn (ví dụ: tối đa 30 ngày) để ngăn chặn tuyệt đối nguy cơ đầy ổ cứng (Disk Full) khiến máy chủ sập nguồn đột ngột.