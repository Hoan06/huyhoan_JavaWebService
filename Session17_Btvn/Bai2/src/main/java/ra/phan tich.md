Lỗi hệ thống trả về 403 Forbidden do hai nguyên nhân cốt lõi trong JwtAuthenticationFilter:

Truyền null vào phương thức xác thực: Đoạn code tokenProvider.validateToken(jwt, null) đã truyền giá trị null thay vì một đối tượng UserDetails hợp lệ. Bên trong JwtTokenProvider, phương thức này cần UserDetails để đối chiếu tên người dùng (username.equals(userDetails.getUsername())). Việc truyền null dẫn đến lỗi NullPointerException hoặc trả về false, khiến luồng xử lý bị đẩy xuống khối catch và bỏ qua việc xác thực.

Thiếu thiết lập Ngữ cảnh Bảo mật (SecurityContext): Ngay cả khi lấy được thông tin UserDetails, bộ lọc hoàn toàn thiếu dòng mã quyết định để thông báo cho Spring Security biết người dùng đã đăng nhập thành công:

Java
SecurityContextHolder.getContext().setAuthentication(authenticationToken);
Do SecurityContextHolder trống rỗng, Spring Security mặc định coi đây là một yêu cầu chưa được xác thực và chặn lại bằng lỗi 403.