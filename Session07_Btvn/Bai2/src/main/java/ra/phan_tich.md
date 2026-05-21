 PHÂN TÍCH CƠ CHẾ CHẶN ĐỨT LUỒNG TRONG SPRING AOP

 Tại sao việc chỉ in log trong `@Before` Advice không thể chặn phương thức gốc?
Cơ chế hoạt động của `@Before`:** Trong Spring AOP, một Advice loại `@Before` được thiết kế để chạy **trước** khi phương thức gốc (Target Method) được thực thi.
Luồng xử lý khi chỉ in log:** Khi một hàm thỏa mãn Pointcut (ví dụ: `addProduct`) được gọi, Proxy của Spring sẽ nhảy vào chạy hàm `verifyUser` trước. Do code cũ chỉ dùng lệnh `System.out.println()`, sau khi in dòng chữ ra màn hình Console, hàm `verifyUser` kết thúc thành công (trả về luồng điều khiển bình thường). Lúc này, Spring Proxy hiểu rằng các bước chuẩn bị đã hoàn tất và an toàn, nó tiếp tục chuyển tiếp lời gọi hàm tới phương thức gốc `addProduct`.
Hệ quả:** Thao tác ghi dữ liệu vẫn diễn ra bình thường, hành động chặn quyền hoàn toàn thất bại.

 Giải pháp kỹ thuật để ngắt luồng (Halt Execution)
Để ngăn chặn tuyệt đối không cho phương thức gốc chạy, phương pháp duy nhất trong `@Before` Advice là **ném ra một RuntimeException (Ngoại lệ thời gian chạy)**.
Khi một Exception được ném ra từ trong Aspect, luồng thực thi thông thường của chương trình sẽ lập tức bị bẻ gãy. Spring Proxy sẽ không gọi phương thức gốc nữa mà lập tức đẩy Exception này ngược trở lại cho tầng gọi (Controller hoặc Client). Nhờ đó, dữ liệu của hệ thống E-commerce được bảo vệ an toàn.