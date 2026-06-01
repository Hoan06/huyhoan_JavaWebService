graph TD
A[Người dùng / Client] -->|1. Gửi Username & Password thô| B(AuthenticationFilter)
B -->|2. Đóng gói thành UsernamePasswordAuthenticationToken| C(AuthenticationManager)
C -->|3. Chuyển tiếp yêu cầu xác thực| D(DaoAuthenticationProvider)

    D -->|4. Gọi loadUserByUsername| E(CustomUserDetailsService)
    E -->|5. Truy vấn SQL/JPA| F[(Database Thư viện)]
    F -->|6. Trả về thông tin User entity| E
    E -->|7. Đóng gói và trả về đối tượng UserDetails| D
    
    D -->|8. Truyền mật khẩu thô từ Client & mật khẩu băm từ UserDetails| G(PasswordEncoder / BCrypt)
    G -->|9. Kiểm tra matches| D
    
    D -->|10. Xác thực thành công: Tạo Authentication object| C
    C -->|11. Lưu thông tin vào SecurityContextHolder| B
    B -->|12. Phản hồi kết quả: Đăng nhập thành công| A

    style E fill:#f9f,stroke:#333,stroke-width:2px
    style G fill:#bbf,stroke:#333,stroke-width:2px
    style F fill:#fbb,stroke:#333,stroke-width:2px