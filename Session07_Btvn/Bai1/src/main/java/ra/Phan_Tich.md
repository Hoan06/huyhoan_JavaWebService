 PHÂN TÍCH HIỆN TRẠNG & VI PHẠM THIẾT KẾ

 Vi phạm nguyên tắc SRP (Single Responsibility Principle)
Hiện trạng:** Lớp `TransactionService` và phương thức `processPayment` đang phải gánh vác cùng lúc **2 nhiệm vụ**:
    1. Xử lý nghiệp vụ chuyển tiền/thanh toán (Nghiệp vụ cốt lõi - Core Concern).
    2. Đo đạc hiệu năng và ghi log (Nghiệp vụ bổ trợ - Cross-cutting Concern).
Hệ quả:** Vi phạm nguyên tắc "Một lớp chỉ nên có một lý do duy nhất để thay đổi". Nếu sau này ngân hàng muốn đổi từ log ra Console sang log vào file hoặc đẩy lên Elasticsearch, ta lại phải nhảy vào sửa code của lớp nghiệp vụ.

 Vi phạm nguyên tắc DRY (Don't Repeat Yourself)
Hiện trạng:** Hệ thống ngân hàng có hàng trăm hàm giao dịch. Nếu hàm nào cũng viết `startTime`, `endTime` rồi trừ cho nhau, code sẽ bị lặp lại vô tội vạ (Code Duplication).
Hệ quả:** Tốn công sức viết code, dễ gõ sai công thức, và cực kỳ khó khăn khi cần tắt/bật tính năng đo hiệu năng này trên môi trường Production.

 Gây ra hiện tượng Code Tangling (Đan xen mã nguồn)
Hiện trạng:** Mã nguồn đo đạc đan xen chặt chẽ với mã nguồn nghiệp vụ.
Hệ quả:** Làm giảm tài liệu hóa tự nhiên của code (code đọc bị rối), lập trình viên khác vào đọc sẽ khó tập trung vào luồng xử lý tiền tệ của ngân hàng.

 Giải pháp khắc phục
Áp dụng kỹ thuật **AOP (Aspect-Oriented Programming)** để tách biệt hoàn toàn phần đo hiệu năng ra một nơi duy nhất (Aspect). Sử dụng `@Around` advice để bọc xung quanh các phương thức trong gói `com.bank.digital.transaction.service`.