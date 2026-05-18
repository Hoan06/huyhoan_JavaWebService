Khái niệm Web Service và Ví dụ thực tế
   Khái niệm: Web Service (Dịch vụ Web) là một tập hợp các công nghệ cho phép các ứng dụng khác nhau – có thể được viết bằng các ngôn ngữ lập trình khác nhau (Java, Python, C#...) và chạy trên các hệ điều hành khác nhau – có thể giao tiếp, trao đổi dữ liệu với nhau thông qua mạng Internet.

Ví dụ thực tế:

Tính năng thanh toán qua ví điện tử/ngân hàng: Khi bạn mua hàng trên Shopee và chọn thanh toán bằng ví MoMo hoặc tài khoản Vietcombank, Shopee sẽ gọi một Web Service của MoMo/Ngân hàng để thực hiện giao dịch và nhận lại kết quả thành công hay thất bại.

Ứng dụng thời tiết: Ứng dụng thời tiết trên điện thoại của bạn không tự đo nhiệt độ. Nó gọi Web Service của một tổ chức khí tượng (như OpenWeatherMap) để lấy dữ liệu thời tiết mới nhất về hiển thị cho bạn.

So sánh SOAP và REST (Dạng gạch đầu dòng)
   Thay vì dùng bảng, dưới đây là sự khác biệt giữa SOAP và REST qua 4 tiêu chí cốt lõi:

Giao thức truyền tải:

SOAP: Bắt buộc phải sử dụng giao thức riêng là HTTP/HTTPS hoặc SMTP, JMS. Nó có các quy tắc rất nghiêm ngặt (chuẩn WS-Security, WS-ReliableMessaging).

REST: Không phải là một giao thức mà là một kiểu kiến trúc. Nó tận dụng trực tiếp các phương thức sẵn có của giao thức HTTP/HTTPS (như GET, POST, PUT, DELETE).

Định dạng dữ liệu chính:

SOAP: Chỉ chấp nhận duy nhất định dạng XML. Tất cả dữ liệu gửi và nhận đều phải bọc trong một "phong bì" XML (SOAP Envelope).

REST: Rất linh hoạt, hỗ trợ nhiều định dạng dữ liệu khác nhau như JSON, XML, Plain Text, HTML... trong đó JSON là phổ biến nhất.

Trạng thái (Stateful / Stateless):

SOAP: Có thể cấu hình để quản lý trạng thái (Stateful - lưu thông tin phiên làm việc) hoặc không lưu trạng thái (Stateless).

REST: Hoàn toàn không lưu trạng thái (Stateless). Mỗi Request gửi từ Client lên Server phải chứa đầy đủ thông tin cần thiết để Server hiểu và xử lý, Server không lưu bất kỳ thông tin ngữ cảnh nào của Client.

Tính dễ dàng triển khai (Độ phức tạp):

SOAP: Phức tạp, khó triển khai hơn do đòi hỏi cấu hình chặt chẽ, định nghĩa file WSDL (file mô tả service) rõ ràng và tốn nhiều băng thông khi truyền tải cấu trúc XML cồng kềnh.

REST: Dễ triển khai, nhẹ nhàng và tốn ít băng thông hơn. Nó dễ học, dễ đọc hiểu đối với lập trình viên và cực kỳ tối ưu cho các ứng dụng Web/Mobile hiện đại.

Chuyển đổi dữ liệu từ JSON sang XML

json : {
"id": 1,
"name": "Mỳ tôm Hảo Hảo",
"quantity": 500,
"price": 3500.0
}

xml :
<?xml version="1.0" encoding="UTF-8"?>
<item>
    <id>1</id>
    <name>Mỳ tôm Hảo Hảo</name>
    <quantity>500</quantity>
    <price>3500.0</price>
</item>

Ưu điểm của JSON so với XML trong truyền tải dữ liệu
   JSON được ưa chuộng hơn XML rất nhiều trong các Web Service hiện đại (đặc biệt là RESTful API) nhờ 2 ưu điểm vượt trội sau:

Nhẹ hơn và tiết kiệm băng thông (Kích thước tệp nhỏ): XML sử dụng cả thẻ đóng và thẻ mở (ví dụ: <name>...</name>), khiến dung lượng file bị phình to bởi các ký tự lặp lại. JSON sử dụng cặp dấu ngoặc và dấu phẩy ("name": "..."), loại bỏ hoàn toàn các thẻ thừa thãi giúp dung lượng dữ liệu truyền qua mạng nhẹ hơn rất nhiều, tăng tốc độ tải của ứng dụng.

Dễ dàng đọc hiểu và xử lý bằng Code (Tối ưu cho JavaScript): Cú pháp của JSON là một dạng mở rộng của JavaScript, nên các ứng dụng Frontend (Web/Mobile) có thể phân tích cú pháp (Parse) JSON thành Object cực kỳ nhanh chóng chỉ bằng một dòng lệnh (JSON.parse()). Trong khi đó, XML đòi hỏi phải sử dụng các bộ DOM Parser phức tạp, tốn tài nguyên phần cứng và mất nhiều bước xử lý hơn.