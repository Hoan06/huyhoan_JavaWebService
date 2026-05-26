Phần 1: Phân tích logic
Dựa vào quy tắc nghiệp vụ và mã nguồn hiện tại, phương thức updateStock đang gặp 2 lỗi logic chính:

Không lưu thay đổi vào cơ sở dữ liệu (Lỗi nghiêm trọng nhất):

Nguyên nhân: Sau khi tính toán và cập nhật số lượng tồn kho mới trên đối tượng Product trong bộ nhớ (product.setStockQuantity(newStock)), lập trình viên đã quên không gọi phương thức save() của ProductRepository.

Hậu quả: Giao dịch kết thúc thành công trả về số lượng mới, nhưng khi hệ thống truy vấn lại hoặc các tiến trình khác đọc dữ liệu, số lượng tồn kho vẫn là con số cũ. Điều này giải thích tại sao đội vận hành thấy số lượng không cập nhật kịp thời khi nhập hàng.

Sử dụng sai loại Exception khi vi phạm quy tắc nghiệp vụ:

Nguyên nhân: Khi newStock < 0, code đang ném ra IllegalStateException.

Hậu quả: Mặc dù vẫn chặn được luồng thực thi (không cho tồn kho âm), nhưng theo chuẩn mực thiết kế, IllegalStateException thường dùng khi trạng thái môi trường/hệ thống không đúng. Ở đây, việc trừ đi lượng hàng lớn hơn số lượng đang có là lỗi do tham số đầu vào (ví dụ: người dùng truyền quantityChange = -100 trong khi kho chỉ có 50). Do đó, ném ra IllegalArgumentException (hoặc một Custom Exception như InsufficientStockException) sẽ phản ánh đúng bản chất nghiệp vụ hơn.