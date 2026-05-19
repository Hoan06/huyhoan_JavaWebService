Nguyên tắc đặt tên đường dẫn (URL)Quy tắc: Đúng. REST bắt buộc dùng danh từ (số nhiều), không dùng động từ.Ví dụ chung: Đúng là GET /books | Sai là POST /deleteBook/1.Ví dụ quản lý sinh viên:Đúng: GET /students và POST /studentsSai: GET /get-all-students và POST /students/delete/12. Các phương thức HTTP chínhGET: Dùng để lấy dữ liệu | Thường không có body.POST: Dùng để tạo mới dữ liệu | Thường có body.PUT: Dùng để thay thế/ghi đè toàn bộ dữ liệu | Thường có body.PATCH: Dùng để cập nhật một phần dữ liệu | Thường có body.DELETE: Dùng để xóa dữ liệu | Thường không có body.3. Phân biệt PUT và PATCH (Sản phẩm gốc: { "id": 1, "name": "Bút bi", "price": 5000 })Kết quả PUT (gửi { "name": "Bút mực" }): Thay thế toàn bộ $\rightarrow$ { "id": 1, "name": "Bút mực", "price": null } (mất giá trị price).Kết quả PATCH (gửi { "name": "Bút mực" }): Chỉ sửa trường gửi $\rightarrow$ { "id": 1, "name": "Bút mực", "price": 5000 } (giữ nguyên price).Khác biệt cốt lõi: PUT là ghi đè tất cả, PATCH là chỉnh sửa cục bộ.4. Mã trạng thái HTTP (Status Codes)200 OK: Yêu cầu thành công, có trả dữ liệu $\rightarrow$ Ví dụ: Lấy danh sách sinh viên thành công.201 Created: Tạo mới tài nguyên thành công $\rightarrow$ Ví dụ: Thêm tài khoản sinh viên mới thành công.204 No Content: Xử lý thành công nhưng không trả về dữ liệu $\rightarrow$ Ví dụ: Xóa sinh viên thành công.400 Bad Request: Lỗi do phía client gửi sai cú pháp/thiếu dữ liệu $\rightarrow$ Ví dụ: Tạo sinh viên nhưng bỏ trống tên.404 Not Found: Không tìm thấy tài nguyên yêu cầu $\rightarrow$ Ví dụ: Tìm sinh viên với ID không tồn tại.500 Internal Server Error: Lỗi hệ thống từ phía server $\rightarrow$ Ví dụ: Database bị sập, code server bị crash.









