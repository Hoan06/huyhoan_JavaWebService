Vai trò và chức năng của UserDetailsService và PasswordEncoder
UserDetailsService trong kiến trúc Spring Security:

Đóng vai trò là một "cầu nối" hoặc "bộ nạp dữ liệu" (Data Fetcher) duy nhất giữa Spring Security và nguồn lưu trữ dữ liệu (Database, LDAP, File...).

Nhiệm vụ duy nhất của nó là định nghĩa phương thức loadUserByUsername(String username). Khi có yêu cầu đăng nhập, Spring Security sẽ truyền username vào hàm này để tìm kiếm. Nếu tìm thấy, nó phải đóng gói dữ liệu từ Database thành một đối tượng kiểu UserDetails (chứa username, password đã mã hóa, danh sách quyền/vai trò) và trả về cho hệ thống xử lý.

PasswordEncoder trong kiến trúc Spring Security:

Đóng vai trò là "bộ mã hóa và đối chiếu mã băm" (Crypto Engine).

Thành phần này thực hiện hai chức năng cốt lõi: Mã hóa mật khẩu thô thành chuỗi băm an toàn khi tạo mới/đổi mật khẩu (encode), và đối chiếu xem mật khẩu thô người dùng vừa nhập khi đăng nhập có khớp với chuỗi băm đang lưu trong Database hay không (matches).

Tại sao lưu trữ mật khẩu Plain Text hoặc dùng NoOpPasswordEncoder lại cực kỳ nguy hiểm?
Rủi ro rò rỉ từ nội bộ (Insider Threats): Ngay cả khi Database được bảo vệ bằng tường lửa, hệ thống chống xâm nhập (IPS) nghiêm ngặt nhất, những người có quyền truy cập trực tiếp vào hệ thống như Quản trị viên Database (DBA), Kỹ sư vận hành (DevOps), hoặc lập trình viên kiểm thử đều có thể nhìn thấy toàn bộ mật khẩu của người dùng.

Lỗ hổng từ các tệp sao lưu (Backup Files) và Log: Các bản backup dữ liệu hàng ngày hoặc các đoạn log vô tình ghi lại truy vấn SQL thường không được bảo vệ nghiêm ngặt như Database chính thức. Nếu hacker lấy được các tệp này, toàn bộ tài khoản sẽ bị chiếm đoạt ngay lập tức.

Hệ lụy dây chuyền (Credential Stuffing): Người dùng thường có thói quen đặt một mật khẩu cho nhiều tài khoản khác nhau (Email, Ngân hàng, Mạng xã hội). Nếu mật khẩu văn bản thuần túy của thư viện bị lộ, hacker sẽ dùng danh sách đó để tấn công chiếm đoạt các tài khoản quan trọng khác của người dùng, gây hậu quả pháp lý nghiêm trọng cho EduLibrary Corp.

Tại sao BCryptPasswordEncoder được khuyến nghị sử dụng rộng rãi?
Cơ chế băm một chiều (One-Way Hashing): BCrypt là thuật toán băm một chiều, nghĩa là từ mật khẩu thô có thể tạo ra chuỗi băm, nhưng hoàn toàn không thể giải mã ngược từ chuỗi băm về lại mật khẩu ban đầu.

Tự động tích hợp muối ngẫu nhiên (Salt): Mỗi lần mã hóa, BCrypt tự động sinh ra một chuỗi muối (Salt) ngẫu nhiên và trộn vào mật khẩu trước khi băm. Vì vậy, hai người dùng có cùng mật khẩu "123456" khi lưu vào database sẽ có hai chuỗi băm hoàn toàn khác nhau. Điều này triệt tiêu hoàn toàn kỹ thuật tấn công bằng bảng tra cứu trước (Rainbow Table Attack).

Cơ chế kéo dài thời gian (Work Factor / Cost): BCrypt cho phép cấu hình một tham số gọi là "độ phức tạp" (Cost). Tham số này ép CPU phải tốn một khoảng thời gian nhất định (ví dụ: 100ms) để tính toán xong một chuỗi băm. Khoảng thời gian này không ảnh hưởng đến trải nghiệm của một người dùng đăng nhập, nhưng biến các cuộc tấn công thử sai hàng triệu lần (Brute-Force Attack) bằng siêu máy tính trở nên bất khả thi vì tiêu tốn quá nhiều thời gian và tài nguyên phần cứng.