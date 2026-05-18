Các thành phần trong HTTP Request và ý nghĩa
   Dựa vào hình ảnh, các thành phần của HTTP Request bao gồm:

Method (Phương thức): POST — Dùng để gửi dữ liệu lên server nhằm tạo mới một tài nguyên (ở đây là tạo mới sản phẩm).

URL (Đường dẫn): /api/sanpham — Địa chỉ endpoint trên server tiếp nhận request này.

HTTP Version: HTTP/1.1

Body (Thân của request): {"ten":"Laptop","gia":15000000,"tonkho":10} — Chuỗi dữ liệu JSON chứa thông tin sản phẩm muốn tạo.

Giải thích ý nghĩa từng Header trong Request:
Host: example.com: Xác định tên miền (domain) của server mà client đang muốn gửi request tới.

Content-Type: application/json: Báo cho server biết rằng dữ liệu được gửi đi trong phần Body có định dạng là JSON.

Authorization: Bearer abc123: Chứa token xác thực (abc123) để chứng minh client có quyền truy cập và thực hiện hành động tạo sản phẩm này trên server.

Content-Length: 48: Kích thước của phần Body tính bằng đơn vị bytes (đoạn chuỗi JSON dài đúng 48 ký tự).

Các thành phần trong HTTP Response và ý nghĩa mã 201
   Các thành phần của HTTP Response bao gồm:

Status Line (Dòng trạng thái): HTTP/1.1 201 Created — Gồm phiên bản HTTP và mã trạng thái của phản hồi.

Body (Thân của response): {"id":101,"ten":"Laptop","gia":15000000,"tonkho":10} — Dữ liệu của sản phẩm sau khi đã được tạo thành công trên server (đã được cấp thêm trường id: 101).

Phân tích mã trạng thái 201 Created:
Thuộc nhóm: 2xx (Nhóm mã báo hiệu hành động được tiếp nhận và xử lý thành công).

Ý nghĩa: Mã 201 Created thông báo rằng request đã thành công và một tài nguyên mới đã được tạo ra thành công trên server.

Giải thích ý nghĩa các Header trong Response:
Date: Mon, 10 Apr 2025 07:30:00 GMT: Thời gian mà server xử lý và trả về response.

Content-Type: application/json: Báo cho client biết dữ liệu trả về ở phần Body là JSON.

Location: /api/sanpham/101: Chỉ ra đường dẫn (URL) trực tiếp để client có thể truy cập vào tài nguyên (sản phẩm) vừa được tạo mới này.

Xử lý trường hợp sản phẩm không tồn tại
   Khi client gửi request GET /api/sanpham/999 nhưng hệ thống không tìm thấy sản phẩm có ID này:

Mã trạng thái trả về: 404 Not Found

Giải thích: Đây là mã lỗi thuộc nhóm 4xx (Lỗi từ phía Client). Nó có ý nghĩa là server hoàn toàn hoạt động bình thường, nhưng không tìm thấy tài nguyên mà client đang yêu cầu dựa trên đường dẫn được cung cấp.

Xử lý khi Server gặp lỗi xử lý không xác định
   Khi server xảy ra lỗi trong quá trình tính toán, xử lý logic nội bộ (ví dụ: code bị lỗi NullPointerException, sập database, hoặc một lỗi không xác định trước):

Mã trạng thái trả về: 500 Internal Server Error

Giải thích: Đây là mã lỗi kinh điển thuộc nhóm 5xx (Lỗi từ phía Server), dùng để thông báo chung rằng server đã gặp phải một tình huống bất ngờ khiến nó không thể hoàn thành request của client, dù cú pháp request của client gửi lên hoàn toàn hợp lệ.