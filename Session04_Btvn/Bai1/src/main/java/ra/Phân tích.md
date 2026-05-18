Phần 1 – Phân tích logic
Nguyên nhân gốc rễ
Client nhận về chuỗi có định dạng lạ như [Movie@3a1b2c3d, Movie@4f2e1a0b] là do dòng lệnh:

Java
return movies.toString();
Khi bạn gọi movies.toString() (với movies là một List<Movie>), Java sẽ duyệt qua từng phần tử trong danh sách và gọi hàm toString() của từng đối tượng Movie.

Tuy nhiên, trong class inner Movie của bạn:

Bạn chưa override (ghi đè) hàm toString().

Do đó, Java sẽ sử dụng hàm toString() mặc định của lớp cha cao nhất là java.lang.Object. Định dạng mặc định này có cấu trúc: Tên_Lớp@Mã_Hash_Code_Dạng_Hex. Đó chính là lý do xuất hiện các chuỗi kiểu Movie@3a1b2c3d.

Tại sao Frontend không đọc được?
Hàm getMovies() của bạn đang khai báo kiểu trả về là String. Khi bạn ép kiểu danh sách thành một chuỗi thuần túy bằng toString(), Spring Boot sẽ hiểu đây là một văn bản thô (text/plain) chứ không phải là cấu trúc dữ liệu JSON (application/json). Kết quả là dữ liệu trả về chỉ là một chuỗi vô nghĩa đối với trình duyệt hoặc mobile app, khiến chúng không thể parse (phân tích) thành object để hiển thị.