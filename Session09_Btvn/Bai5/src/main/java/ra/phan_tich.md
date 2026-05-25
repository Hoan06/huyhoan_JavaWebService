 Phân tích: Sự vượt trội của JSON Log so với Text Log trên các hệ thống Giám sát (ELK / Datadog)

Khi ứng dụng vận hành ở quy mô lớn, việc sử dụng Log dạng Text truyền thống (Unstructured Log) bộc lộ nhiều hạn chế. Thay vào đó, Log có cấu trúc dạng JSON (Structured Log) là tiêu chuẩn bắt buộc vì 2 lý do cốt lõi sau:

Khả năng Phân tích (Parsing) tự động và Tối ưu hóa Truy vấn cực nhanh:
    - Với Text Log: Các hệ thống như Elasticsearch hoặc Datadog phải sử dụng các bộ lọc biểu thức chính quy (Regex/Grok Pattern) rất phức tạp để "bóc tách" xem đâu là ngày tháng, đâu là level, đâu là mã lỗi. Việc này vừa dễ sai sót khi dev thay đổi định dạng in, vừa tiêu tốn cực kỳ nhiều tài nguyên CPU của hệ thống monitor.
    - Với JSON Log: Các trường dữ liệu đã được định nghĩa rõ ràng dưới dạng Key-Value (ví dụ: `{"level":"ERROR", "requestId":"xyz"}`). Hệ thống giám sát chỉ cần đọc trực tiếp (Native Indexing) mà không cần parse. Lập trình viên có thể truy vấn ngay lập tức bằng các câu lệnh chính xác như: `level: "ERROR" AND requestId: "123-abc"` thay vì phải tìm kiếm chuỗi văn bản (Full-text search) mơ hồ và chậm chạp.

Khả năng Trích xuất Chỉ số (Metrics) và Vẽ biểu đồ Giám sát theo Thời gian thực:
    - JSON Log biến các dòng chữ vô tri thành các "dữ liệu số" có cấu trúc. Từ các trường như `errorCode`, `executionTime`, hay `userId` được bóc tách từ JSON, hệ thống ELK/Datadog có thể dễ dàng gom nhóm (Aggregation) để thiết lập các biểu đồ trực quan (Dashboards):
        - Biểu đồ đường (Line Chart) hiển thị tần suất lỗi 500 tăng vọt theo từng phút.
        - Biểu đồ tròn (Pie Chart) thống kê tỷ lệ các loại mã lỗi (`DB_ERR`, `AUTH_ERR`) đang xảy ra trên hệ thống để đội ngũ vận hành phản ứng kịp thời. Điều này là bất khả thi nếu chỉ dùng Text Log thô.