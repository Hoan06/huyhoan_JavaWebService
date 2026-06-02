Lỗi Invalid JWT xảy ra do bất đối xứng về Secret Key giữa quá trình ký (Sign) và quá trình xác thực (Verify):

Khi tạo: Hệ thống dùng key được tạo ngẫu nhiên lần 1 để ký số vào token.

Khi xác thực: Hệ thống lại tạo một differentKey hoàn toàn mới (ngẫu nhiên lần 2) để giải mã.

Theo nguyên lý mã hóa đối xứng (HMAC-SHA256), chữ ký của JWT chỉ có thể được xác thực thành công khi và chỉ khi sử dụng chính xác cùng một Secret Key đã dùng để ký ban đầu. Việc dùng sai key khiến Signature không khớp, thư viện JJWT sẽ lập tức chặn lại và ném ra ngoại lệ bảo mật.