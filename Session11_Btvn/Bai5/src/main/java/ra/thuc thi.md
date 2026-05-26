Phần 2: Thực thi - Chiến lược kiểm thử toàn diện
Mô hình Kim tự tháp kiểm thử (Test Pyramid)
   Tầng Unit Tests (Chiếm ~60%): Tập trung kiểm tra logic cô lập trong các lớp Service, Helper, Domain Entity. Chi phí rẻ, thời gian chạy cực nhanh (vài mili-giây).

Tầng Contract Tests (Chiếm ~15%): Đảm bảo "giao kèo" API giữa các service không bị phá vỡ, thay thế cho các bài test tích hợp cồng kềnh.

Tầng Integration Tests (Chiếm ~20%): Kiểm tra sự tương tác giữa mã nguồn với Database, Cache hoặc Network thật.

Tầng End-to-End (E2E) Tests (Chiếm ~5%): Kiểm tra một luồng nghiệp vụ hoàn chỉnh từ giao diện (UI) hoặc API Gateway đi qua toàn bộ các service. Chỉ test các kịch bản cốt lõi vì chạy chậm và tốn tài nguyên.

Công cụ và Kỹ thuật đề xuất
   Tầng Unit Test: Sử dụng JUnit 5, Mockito và AssertJ để mock toàn bộ dependency, tập trung kiểm tra các logic rẽ nhánh, tính toán và validate input.

Tầng Integration Test: Sử dụng Spring Boot Test (@DataJpaTest, @WebMvcTest) kết hợp với Testcontainers để tự động dựng một Database thật (PostgreSQL/MySQL) bằng Docker khi chạy test, tránh dùng H2 để hạn chế sai lệch cú pháp SQL.

Tầng Contract Test: Sử dụng Pact Framework để áp dụng kỹ thuật Consumer-driven Contract Testing (CDC).

Tầng E2E Test: Sử dụng REST Assured cho Backend API và Cypress/Playwright cho Frontend UI để giả lập một phiên đặt lịch trọn vẹn từ đầu đến cuối.

Chiến lược cho Testing Coverage (Quality Gates)
   Cấu hình JaCoCo: Tích hợp trực tiếp vào build tool (Maven/Gradle), thiết lập chốt chặn tự động với ngưỡng tối thiểu: Line Coverage đạt 80% và Branch Coverage đạt 70%.

Cơ chế hoạt động: Nếu mã nguồn mới không viết đủ test để phủ hết các dòng code và các nhánh if/else, JaCoCo sẽ chủ động làm hỏng (fail) quá trình build ngay tại máy local của lập trình viên.

Mục đích: Ràng buộc trách nhiệm của đội phát triển, ngăn chặn code rác hoặc code thiếu test lọt lên hệ thống Git chung.

Giải pháp kiểm thử giao tiếp giữa các services (Contract Testing)
   Consumer định nghĩa: BookingService (Consumer) tự viết một file định nghĩa (Contract JSON) mô tả chính xác cấu trúc request gửi đi và response kỳ vọng nhận về từ UserService.

Lưu trữ tập trung: File JSON này được đẩy lên một server chung gọi là Pact Broker.

Provider xác thực: Khi UserService (Provider) chạy pipeline của riêng nó, Pact sẽ tự động kéo file JSON từ Broker về và giả lập request bắn vào UserService thật.

Lợi ích: Nếu UserService thay đổi cấu trúc API làm lệch contract, hệ thống sẽ báo lỗi ngay lập tức mà không cần phải khởi động đồng thời cả hai dịch vụ lên để test.

Tích hợp vào quy trình CI/CD
   Giai đoạn Commit (Mỗi Pull Request): Hệ thống tự động kích hoạt Unit Test và kiểm tra Quality Gate của JaCoCo. Nếu không đạt chỉ số coverage, Pull Request sẽ bị chặn, không cho phép Merge.

Giai đoạn Tích hợp (Sau khi Merge): Hệ thống tự động kích hoạt Testcontainers để chạy Integration Test với DB thật và chạy Pact để kiểm tra tính tương thích API giữa các service.

Giai đoạn Đóng gói (Hằng đêm - Nightly Build): Tự động deploy các bản build mới nhất lên môi trường Staging độc lập và chạy toàn bộ cấu hình E2E Tests để quét lỗi, đảm bảo mục tiêu chất lượng duy trì dưới 2 lỗi nghiêm trọng mỗi tháng.