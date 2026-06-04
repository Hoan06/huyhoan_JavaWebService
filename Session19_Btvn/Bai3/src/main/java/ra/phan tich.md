Phân tích các phương thức hiện có (Giả định hệ thống cũ)
   RefreshTokenService và RefreshTokenRepository ở phiên bản cơ bản thường chỉ thực hiện các thao tác lưu trữ tuyến tính bao gồm: Tạo token gắn liền với userId, tìm kiếm token để gia hạn Access Token, và xóa một token cụ thể khi người dùng chọn đăng xuất.
   Cơ chế lưu trữ này có cấu trúc dữ liệu phẳng dạng $1:1$ hoặc $1:N$ cơ bản giữa User và RefreshToken nhưng thiếu đi các siêu dữ liệu bổ trợ (metadata) để phân loại nguồn gốc của các token đó.
Đánh giá tính hiệu quả của cơ chế thu hồi token hiện tại
   Kém linh hoạt và rủi ro cao: Khi người dùng nhấn đăng xuất, hệ thống cũ chỉ thực hiện xóa duy nhất bản ghi mã token hiện tại được gửi kèm trong request.
   Không thể quản trị phiên chéo: Trong kịch bản người dùng bị mất điện thoại hoặc truy cập tài khoản từ máy tính công cộng nhưng quên không đăng xuất, họ hoàn toàn bất lực trong việc hủy phiên làm việc đó từ một thiết bị an toàn khác (như máy tính cá nhân ở nhà), bởi backend không có cơ chế liên kết và chọn lọc để xóa các token của thiết bị cụ thể kia.
Hạn chế hiện tại và phương án cải tiến chi tiết
   Hạn chế định danh: Hệ thống không biết token nào thuộc về điện thoại, token nào thuộc về máy tính bảng hay trình duyệt web.
   Hạn chế rác dữ liệu: Các token hết hạn không tự động biến mất, chúng tích tụ trong cơ sở dữ liệu làm phình to dung lượng table, giảm tốc độ index và query tìm kiếm theo thời gian.
   Phương án cải tiến:
   Tích hợp trường định danh thiết bị (deviceId) vào thực thể RefreshToken. Chuỗi này sẽ do Client sinh ra (UUID) hoặc lấy từ thông tin phần cứng/trình duyệt và gửi lên qua HTTP Header.
   Xây dựng hai cơ chế đăng xuất tách biệt: Đăng xuất phiên hiện tại (Xóa cặp userId + deviceId) và Đăng xuất toàn bộ các thiết bị (Xóa toàn bộ bản ghi theo userId).
   Thiết kế tác vụ dọn dẹp (Cleanup job) định kỳ quét và xóa các bản ghi có trường thời gian hết hạn nhỏ hơn thời gian hiện tại (expiration < NOW()).

