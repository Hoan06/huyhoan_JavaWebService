Để đạt được mức độ bảo mật tối ưu, linh hoạt và dễ mở rộng cho nền tảng Game Arcade, giải pháp tốt nhất là áp dụng chiến lược Bảo mật đa tầng (Layered Security), kết hợp cả hai phương pháp:

Phân quyền dựa trên URL (URL-based Authorization) trong SecurityConfig
   Phạm vi áp dụng: Áp dụng cho các cụm API có chung tiền tố định tuyến cấu trúc tĩnh như /api/admin/ hoặc /api/moderator/.

Lý do lựa chọn: Đây đóng vai trò như tầng "Cửa khẩu/Tường lửa" vòng ngoài. Nó giúp hệ thống chặn đứng các truy cập trái phép ở mức thô một cách nhanh chóng nhất mà không cần phải tải dữ liệu vào Context của Controller. Điều này giúp tối ưu hiệu năng và đảm bảo không một API quản trị nào bị bỏ sót do quên gắn annotation.

Phân quyền cấp phương thức (Method-level Authorization) bằng @PreAuthorize
   Phạm vi áp dụng: Áp dụng cho các API chứa logic nghiệp vụ động, phụ thuộc vào dữ liệu đầu vào hoặc ngữ cảnh người dùng (ví dụ: người dùng chỉnh sửa bình luận của chính mình, mua vật phẩm kiểm tra số dư).

Lý do lựa chọn: Đây là tầng "Kiểm soát an ninh" vòng trong. Nhờ ngôn ngữ biểu thức SpEL, @PreAuthorize cho phép kiểm tra các điều kiện phức tạp (như đối chiếu userId trong token với chủ sở hữu của vật phẩm/bình luận).