Lỗ hổng Hardcode Secret Key (HARDCODED_SECRET_KEY)
   Bản chất: Khóa bí mật dùng để ký và xác thực mã JWT được viết trực tiếp dưới dạng một chuỗi hằng số trong mã nguồn. Ngoài ra, chuỗi hằng số này quá ngắn, không đáp ứng tiêu chuẩn độ dài tối thiểu của thuật toán HS256.

Kịch bản khai thác: Kẻ tấn công có thể lấy được chuỗi khóa này thông qua việc dịch ngược mã nguồn (reverse-engineering), khai thác các lỗ hổng rò rỉ mã nguồn (như lộ file cấu hình trên GitHub) hoặc đọc trực tiếp từ bộ nhớ khi ứng dụng chạy.

Hậu quả: Khi có được Secret Key, kẻ tấn công hoàn toàn có thể tự giả mạo (forge) chữ ký để tạo ra các token hợp lệ với danh nghĩa của bất kỳ tài khoản nào, bao gồm cả tài khoản quản trị viên (admin), bỏ qua toàn bộ cơ chế đăng nhập nhằm chiếm quyền kiểm soát hệ thống, thực hiện các lệnh giao dịch chứng khoán bất hợp pháp.

Lỗ hổng thời gian sống Access Token quá dài (ACCESS_TOKEN_EXPIRATION_DAYS = 30)
   Bản chất: Access Token được thiết lập thời gian hết hạn lên đến 30 ngày.

Kịch bản khai thác: Khi người dùng truy cập từ mạng công cộng hoặc thiết bị nhiễm mã độc, Access Token có thể bị đánh cắp thông qua các cuộc tấn công đánh chặn (Man-in-the-Middle) hoặc chiếm quyền kiểm soát LocalStorage/Cookie của trình duyệt.

Hậu quả: Vì token có hiệu lực đến 30 ngày, kẻ tấn công có một khoảng thời gian cực kỳ dài để sử dụng token ăn cắp đó liên tục gửi request giả mạo lên backend mà không bị hệ thống từ chối, gây thiệt hại lớn về tài sản trước khi người dùng thực sự phát hiện ra phiên làm việc bị xâm nhập.