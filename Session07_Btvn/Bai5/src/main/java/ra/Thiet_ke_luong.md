[ Client / Frontend ]
│
▼  (1) Gửi Request Giao Dịch
[ Tầng Controller (Web Layer) ]
│  (Nhận dữ liệu thô, không chứa logic validate nghiệp vụ)
▼  
[ Tầng AOP (Aspect Layer) ] ◄─── Chốt chặn Giám sát & Kiểm duyệt Tập trung
│   ├─ Chặn 1: Bẫy dữ liệu (Số tiền âm, Địa chỉ ví dưới 5 ký tự)
│   └─ Chặn 2: Đánh giá rủi ro (Số tiền lớn hơn 10,000 USD)
│  (Nếu vi phạm bất kỳ chốt chặn nào ➔ Ném Exception lập tức)
▼  
[ Tầng Service (Business Layer) ]
│  (Sạch tuyệt đối - Chỉ chạy khi dữ liệu đã AN TOÀN)
│  └─ Logic duy nhất: Thực thi cập nhật số dư / Lưu lịch sử giao dịch.
▼
[ Global Exception Handler ] ◄── Chốt chặn gom lỗi cuối cùng (Nếu có Exception)
│
▼  (2) Đóng gói dữ liệu lỗi thành JSON HTTP 400 Chuẩn
[ Client / Frontend ]