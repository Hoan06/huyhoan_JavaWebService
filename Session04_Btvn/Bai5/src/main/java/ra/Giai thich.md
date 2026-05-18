Phần 2 – Giải thích quyết định thiết kế và Phân tích chuyên sâu
Trong thiết kế hệ thống thực tế, việc lựa chọn URL và Method đóng vai trò quyết định đến tính mở rộng và độ tường minh của hệ thống. Dưới đây là lý do chọn lựa:

Tại sao thiết kế /api/v1/trips/search thay vì gộp chung vào /api/v1/trips?
   Lý do: Bạn hoàn toàn có thể gộp chung thành GET /api/v1/trips?from=Hanoi&to=HCM. Tuy nhiên, trong nghiệp vụ đặt vé xe, việc "Tìm kiếm tuyến đường" (Search) thường đi kèm các logic nghiệp vụ rất nặng như: tính toán thời gian chạy, kiểm tra số ghế trống real-time, gợi ý tuyến đường gần nhất.

Việc tách riêng ra endpoint /trips/search giúp tách biệt logic xử lý code ở tầng Backend (Controller/Service), dễ cấu hình cache riêng cho tính năng tìm kiếm và giúp file log hệ thống tường minh hơn rất nhiều.

Tại sao Đặt vé dùng POST /api/v1/tickets?
   Bản chất: Hành động đặt vé là hành động tạo ra một tài nguyên mới hoàn toàn trong cơ sở dữ liệu (tạo ra một hóa đơn/vé xe).

Hành động này do Server tự quản lý và sinh ra ticketId, đồng thời nó không có tính Idempotent (nếu client gọi 2 lần, hệ thống sẽ trừ tiền và đặt 2 ghế khác nhau). Vì vậy, POST là lựa chọn duy nhất đúng chuẩn.

Tình huống phân vân: Cập nhật thông tin hành khách dùng PUT hay PATCH?
   Đây là bẫy thiết kế rất nhiều lập trình viên gặp phải khi đi làm. Đối với nghiệp vụ “Cập nhật thông tin hành khách trên vé (họ tên, số điện thoại)”:

Nếu chọn PUT: Client sẽ phải gửi lên toàn bộ cấu trúc của chiếc vé (bao gồm cả ticketId, tripId, seatNumber, price, bookingDate...). Nếu thiếu, các trường không truyền lên sẽ bị ghi đè thành null hoặc lỗi dữ liệu. Điều này rất nguy hiểm vì thông tin như seatNumber (số ghế) hay price (giá tiền) tuyệt đối không được phép chỉnh sửa tùy tiện khi khách hàng chỉ muốn đổi số điện thoại.

Quyết định chọn PATCH: PATCH được thiết kế ra để cập nhật một phần (partial update) tài nguyên. Client chỉ cần bắn lên những gì họ muốn thay đổi:

JSON
{
"customerName": "Nguyễn Văn A",
"phone": "0987654321"
}
Server sẽ chỉ cập nhật đúng 2 trường này và giữ nguyên toàn bộ thông tin cốt lõi khác của vé. Do đó, PATCH an toàn và tối ưu băng thông hơn rất nhiều so với PUT trong trường hợp này.

Tại sao Hủy vé dùng DELETE thay vì POST /tickets/cancel?
   Tư duy RESTful: REST coi mọi thứ là "Tài nguyên" (Resource) chứ không coi là "Hành động" (Action). Tránh đưa các động từ như /cancel, /edit, /delete lên cấu trúc URL.

Bản chất của việc hủy vé là triệt tiêu sự hiệu lực của tấm vé đó trên hệ thống (hoặc xóa khỏi hàng đợi). Do đó, việc định vị chính xác tấm vé qua ID và dùng phương thức DELETE là cách thể hiện ngắn gọn, rõ ràng và chuẩn mực nhất của kiến trúc REST.