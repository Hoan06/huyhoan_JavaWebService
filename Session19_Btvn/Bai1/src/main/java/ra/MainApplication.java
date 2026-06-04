package ra;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class MainApplication {
    public static void main(String[] args) {
        TokenService tokenService = new TokenService();

        long expectedMs = 15 * 60 * 1000;
        long actualMs = tokenService.getExpirationMs();

        System.out.println("====== KIỂM TRA CẤU HÌNH THỜI GIAN ======");
        System.out.println("Thời gian mong đợi (15 phút) : " + expectedMs + " ms");
        System.out.println("Thời gian thực tế cấu hình   : " + actualMs + " ms");
        System.out.println("Kết quả so khớp cấu hình     : " + (expectedMs == actualMs ? "THÀNH CÔNG" : "THẤT BẠI"));

        System.out.println("\n====== KIỂM TRA ĐỌC DỮ LIỆU TOKEN (JJWT 0.13.x) ======");
        String username = "rikkei_student";
        String token = tokenService.generateAccessToken(username);
        System.out.println("Token sinh ra: " + token);

        Claims claims = Jwts.parser()
                .verifyWith(tokenService.getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        System.out.println("Chủ thể Token (Subject)     : " + claims.getSubject());
        System.out.println("Thời gian phát hành (Issued): " + claims.getIssuedAt());
        System.out.println("Thời gian hết hạn (Expired) : " + claims.getExpiration());

        long durationMs = claims.getExpiration().getTime() - claims.getIssuedAt().getTime();
        long durationMinutes = durationMs / 1000 / 60;
        System.out.println("Vòng đời Token thực tế      : " + durationMinutes + " phút");

        boolean isValid = tokenService.validateToken(token);
        System.out.println("Trạng thái hàm validateToken(): " + (isValid ? "HỢP LỆ (True)" : "KHÔNG HỢP LỆ (False)"));
    }
}