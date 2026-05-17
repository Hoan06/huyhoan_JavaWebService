Phần 1 - Phân tích logic
Vai trò của HTTP Status Codes trong RESTful API vững chắc (Robust API)
   HTTP Status Codes đóng vai trò là một ngôn ngữ chung chuẩn quốc tế giữa Server và Client. Thay vì bắt Client phải phân tích cú pháp (parse) nội dung phần thân phản hồi (Response Body) để đoán xem tác vụ thành công hay thất bại, HTTP Status Code cung cấp thông tin trạng thái lập tức ở tầng giao thức (Protocol level).

Tiết kiệm tài nguyên: Client có thể nhanh chóng rẽ nhánh xử lý logic (Hiển thị thông báo, chuyển trang, hoặc thử lại) ngay khi đọc được mã trạng thái từ Header.

Tính nhất quán: Giúp tích hợp hệ thống mượt mà, đặc biệt là trong kiến trúc Microservices hoặc khi làm việc với nhiều đội ngũ phát triển Client (Web, Mobile, IoT) khác nhau.

Tác hại của việc trả về null hoặc {} thay vì ResponseEntity với 404 NOT_FOUND
   Khi một mặt hàng không tồn tại, nếu Server vẫn trả về mã định danh 200 OK kèm theo body là null hoặc một chuỗi rỗng {}:

Đánh lừa Client: Mã 200 OK khẳng định với Client rằng "Yêu cầu đã thành công và dữ liệu hợp lệ". Khi Client tin tưởng vào mã này và tiến hành bóc tách các thuộc tính như item.getName(), hệ thống sẽ lập tức sập hoặc tung ra lỗi NullPointerException (trên Java/Android) hoặc Cannot read properties of null (trên JavaScript/Frontend).

Tăng độ phức tạp cho Client: Phía Client sẽ phải viết thêm rất nhiều câu lệnh điều kiện if (data == null || data.id == undefined) ở mọi nơi để phòng thủ lỗi, khiến mã nguồn Client trở nên cồng kềnh và dễ sót trường hợp.

Tại sao Jackson Dataformat XML là cần thiết cho Content Negotiation?
   Theo mặc định, Spring Boot Starter Web chỉ tích hợp sẵn bộ chuyển đổi dữ liệu của Jackson dành riêng cho định dạng JSON.
   Khi Client gửi một request với Header Accept: application/xml (yêu cầu cấu trúc dữ liệu trả về là XML), Spring Boot sẽ thực hiện quá trình Content Negotiation (Thương lượng nội dung). Nếu không có thư viện jackson-dataformat-xml, Spring Boot sẽ không tìm thấy bất kỳ HttpMessageConverter nào có khả năng chuyển đổi (marshal) đối tượng Java sang định dạng XML. Kết quả là Server sẽ lập tức từ chối và trả về mã lỗi 406 Not Acceptable.

Thư viện jackson-dataformat-xml cung cấp thêm bộ chuyển đổi cấu trúc dữ liệu XML, giúp Spring tự động đọc hiểu Header Accept để quyết định chuyển đổi đối tượng Java thành định dạng tương ứng một cách linh hoạt.