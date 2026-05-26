Phần 1: Phân tích logic
Dựa vào quy tắc nghiệp vụ và mã nguồn hiện tại, hệ thống đang gặp 2 lỗi logic nghiêm trọng dẫn đến việc tính sai phí:

Lỗi tính phí cân nặng (Xử lý số lẻ):

Nguyên nhân: Mã nguồn đang sử dụng Math.floor(weightKg - 1) để tính phần cân nặng vượt mức. Math.floor() là hàm làm tròn xuống.

Ví dụ sai: Khi đơn hàng nặng 1.5kg, hệ thống tính: 1.5 - 1 = 0.5kg vượt mức. Sau đó gọi Math.floor(0.5) = 0. Kết quả là khách hàng không bị tính thêm 10.000 VND cho 0.5kg lẻ này.

Giải pháp: Theo quy tắc "mỗi kg tiếp theo (hoặc phân số của kg) tính thêm", chúng ta phải làm tròn lên bằng hàm Math.ceil().

Lỗi tính phí khoảng cách (Tính sai điểm biên và logic luỹ tiến):

Nguyên nhân thứ nhất (Không trừ phần miễn phí): Quy định ghi "dưới 10km không tính thêm phí", tức là 10km đầu tiên luôn miễn phí. Nhưng code hiện tại lấy nguyên tổng khoảng cách nhân với giá tiền (VD: distanceKm * 5000). Nếu đi 15km, code đang tính tiền cho cả 15km thay vì chỉ tính 5km vượt mức.

Nguyên nhân thứ hai (Nghịch lý giá tại ngưỡng biên): Code đang dùng bậc giá cố định thay vì tính luỹ tiến (tiered pricing).

Giao 49.9km, phí khoảng cách là: 49.9 * 5000 = 249.500 VND.

Giao 50km (rơi vào nhánh else), phí khoảng cách là: 50 * 4000 = 200.000 VND.

Khách hàng giao xa hơn lại trả ít tiền hơn.

Giải pháp: Phải áp dụng cách tính luỹ tiến: 10km đầu (0đ) + 40km tiếp theo (từ 10 đến 50) x 5.000đ + phần trên 50km x 4.000đ.