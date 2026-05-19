Lấy danh sách sách (có phân trang & lọc theo tác giả)
   Method: GET

URL: /books

Query params: ?page=1&limit=10&author=Nguyen+Nhat+Anh

Status thành công: 200 OK

Status lỗi: 400 Bad Request (Ví dụ: truyền sai kiểu dữ liệu phân trang)

2Thêm sách mới
   Method: POST

URL: /books

Query params: Không có (Dữ liệu truyền trong Request Body)

Status thành công: 201 Created

Status lỗi: 400 Bad Request (Ví dụ: thiếu trường title hoặc quantity < 0)

Cập nhật số lượng sách (chỉ 1 trường)
   Method: PATCH

URL: /books/{id} (Ví dụ: /books/1)

Query params: Không có (Dữ liệu quantity truyền trong Request Body)

Status thành công: 200 OK

Status lỗi: 404 Not Found (Ví dụ: ID sách không tồn tại), 400 Bad Request (Số lượng không hợp lệ)

Xóa sách
   Method: DELETE

URL: /books/{id} (Ví dụ: /books/1)

Query params: Không có

Status thành công: 204 No Content

Status lỗi: 404 Not Found (Ví dụ: sách cần xóa không tồn tại)

Lấy danh sách thẻ mượn của một sách (Sub-resource)
   Method: GET

URL: /books/{id}/loans (Ví dụ: /books/1/loans)

Query params: ?page=1&limit=10 (nếu cần phân trang lịch sử mượn)

Status thành công: 200 OK

Status lỗi: 404 Not Found (Ví dụ: ID sách không tồn tại)

Tạo thẻ mượn mới
   Method: POST

URL: /loans

Query params: Không có (Dữ liệu bookId, borrowerName... truyền trong Request Body)

Status thành công: 201 Created

Status lỗi: 400 Bad Request (Ví dụ: sách đã hết số lượng quantity = 0 để cho mượn)

Trả sách (Cập nhật ngày trả)
   Method: PATCH

URL: /loans/{id} (Ví dụ: /loans/5)

Query params: Không có (Dữ liệu returnDate truyền trong Request Body hoặc server tự động lấy ngày giờ hiện tại)

Status thành công: 200 OK

Status lỗi: 404 Not Found (Ví dụ: không tìm thấy mã thẻ mượn này)

Lấy chi tiết thông tin một thẻ mượn cụ thể
   Method: GET

URL: /loans/{id} (Ví dụ: /loans/5)

Query params: Không có

Status thành công: 200 OK

Status lỗi: 404 Not Found (Ví dụ: Thẻ mượn không tồn tại trên hệ thống)