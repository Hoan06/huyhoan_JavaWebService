Dựa trên cấu hình SecurityFilterChain hiện tại:

Java
.authorizeHttpRequests(authorize -> authorize
.requestMatchers("/").permitAll()
.anyRequest().authenticated()
)
Nguyên nhân gốc rễ:
Thiếu quy tắc kiểm tra Vai trò (Role-based Authorization): Cấu hình hiện tại hoàn toàn không sử dụng các phương thức kiểm tra quyền như .hasRole("ADMIN") hay .hasAuthority("ROLE_ADMIN") cho pattern /admin/.

Cơ chế hoạt động của anyRequest().authenticated(): Phương thức này chỉ kiểm tra xem Request đó đã được xác thực (đăng nhập) hay chưa. Do đó, ngay sau khi tài khoản user đăng nhập thành công, Spring Security coi như điều kiện .authenticated() đã được thỏa mãn.

Thứ tự quét của Spring Security: Khi có request gửi đến /admin/orders, Spring Security sẽ quét qua danh sách cấu hình từ trên xuống dưới:

Không khớp với / (.permitAll()).

Rơi vào trường hợp còn lại là .anyRequest(). Vì user đã đăng nhập, hệ thống lập tức cho phép truy cập mà không quan tâm user đó giữ chức vụ hay vai trò gì trong hệ thống.