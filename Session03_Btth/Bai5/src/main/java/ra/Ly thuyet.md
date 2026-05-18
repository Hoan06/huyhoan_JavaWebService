Thiết kế các Endpoint cho hệ thống Quản lý Sách
   Dưới đây là thiết kế chi tiết cho 6 chức năng yêu cầu, tuân thủ đúng chuẩn RESTful API:

Chức năng 1: Lấy danh sách tất cả sách
HTTP Method: GET

Đường dẫn (URL): /api/v1/books

Mã trạng thái thành công: 200 OK (Trả về mảng danh sách toàn bộ sách).

Mã trạng thái lỗi có thể xảy ra:

500 Internal Server Error: Hệ thống database của thư viện bị sập, không thể truy vấn dữ liệu.

401 Unauthorized: Client chưa đăng nhập tài khoản hệ thống thư viện, không có quyền xem thông tin.

Chức năng 2: Lấy chi tiết một cuốn sách theo ID
HTTP Method: GET

Đường dẫn (URL): /api/v1/books/{id} (Ví dụ: /api/v1/books/12)

Mã trạng thái thành công: 200 OK (Trả về Object chứa thông tin chi tiết của cuốn sách).

Mã trạng thái lỗi có thể xảy ra:

404 Not Found: Không tìm thấy cuốn sách nào có ID tương ứng trong hệ thống.

400 Bad Request: ID truyền vào không hợp lệ (Ví dụ: truyền vào chuỗi chữ /books/abc thay vì số nguyên).

Chức năng 3: Thêm một cuốn sách mới
HTTP Method: POST

Đường dẫn (URL): /api/v1/books

Mã trạng thái thành công: 201 Created (Sách đã được tạo mới thành công trên hệ thống).

Mã trạng thái lỗi có thể xảy ra:

400 Bad Request: Dữ liệu gửi lên bị thiếu các trường bắt buộc (ví dụ: không có title hoặc author).

415 Unsupported Media Type: Client gửi dữ liệu dạng Text thuần hoặc XML, trong khi API chỉ chấp nhận JSON.

Chức năng 4: Cập nhật toàn bộ thông tin sách theo ID
HTTP Method: PUT

Đường dẫn (URL): /api/v1/books/{id}

Mã trạng thái thành công: 200 OK (Trả về thông tin sách sau khi đã được cập nhật hoàn chỉnh).

Mã trạng thái lỗi có thể xảy ra:

404 Not Found: Cuốn sách muốn cập nhật không tồn tại trong hệ thống.

400 Bad Request: Năm xuất bản (year) gửi lên lớn hơn năm hiện tại (dữ liệu phi logic).

Chức năng 5: Xóa một cuốn sách theo ID
HTTP Method: DELETE

Đường dẫn (URL): /api/v1/books/{id}

Mã trạng thái thành công: 204 No Content (Xóa thành công và không cần trả về dữ liệu trong Body).

Mã trạng thái lỗi có thể xảy ra:

404 Not Found: Cuốn sách cần xóa không tồn tại.

403 Forbidden: Người dùng là độc giả bình thường, không có quyền Quản trị viên (Admin) để xóa sách.

Chức năng 6: Tìm sách theo tác giả
HTTP Method: GET

Đường dẫn (URL): /api/v1/books?author={name} (Ví dụ: /api/v1/books?author=Nam_Cao)

Mã trạng thái thành công: 200 OK (Trả về mảng chứa các cuốn sách của tác giả đó, hoặc mảng rỗng nếu không có).

Mã trạng thái lỗi có thể xảy ra:

400 Bad Request: Tham số query author bị bỏ trống (/books?author=).

500 Internal Server Error: Lỗi kết nối tìm kiếm cơ sở dữ liệu.

Câu hỏi lý thuyết: Phân biệt PUT và PATCH
   Sự khác nhau giữa PUT và PATCH:
   PUT (Cập nhật toàn bộ - Replace): Phương thức này yêu cầu Client phải gửi lên toàn bộ các trường thông tin của đối tượng. Nếu Client gửi thiếu một trường nào đó, giá trị cũ của trường đó trên Server sẽ bị xóa bỏ hoàn toàn hoặc chuyển về giá trị mặc định (null hoặc 0).

PATCH (Cập nhật một phần - Modify): Phương thức này cho phép Client chỉ cần gửi lên những trường dữ liệu cần thay đổi. Những trường không được gửi lên sẽ được Server giữ nguyên giá trị cũ mà không bị ảnh hưởng.

Áp dụng vào Bài tập cập nhật sách:
Trong bài tập thiết kế API quản lý sách này:

Yêu cầu của đề bài là: "Cập nhật toàn bộ thông tin sách theo id". Do đó, việc lựa chọn phương thức PUT là hoàn toàn chính xác và phù hợp với đặc tả thiết kế.

Lý do: Khi người quản trị thư viện muốn chỉnh sửa thông tin sách bằng PUT, họ sẽ lấy toàn bộ dữ liệu hiện tại ra một form, sửa đổi các mục cần thiết rồi gửi lại toàn bộ form đó lên để thay thế hoàn toàn bản ghi cũ, đảm bảo tính toàn vẹn và nhất quán của dữ liệu sách trên Database.