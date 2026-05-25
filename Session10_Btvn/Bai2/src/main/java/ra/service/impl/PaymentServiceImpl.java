package ra.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.exception.BusinessException;
import ra.model.dto.request.PaymentRequestDTO;
import ra.model.dto.response.PaymentResponseDTO;
import ra.model.entity.Order;
import ra.model.entity.UserAccount;
import ra.repository.OrderRepository;
import ra.repository.UserAccountRepository;
import ra.service.IPaymentService;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService {

    private final UserAccountRepository accountRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentResponseDTO processPayment(PaymentRequestDTO request) {
        log.info("[PAYMENT START] Tiếp nhận xử lý thanh toán đơn hàng ID: {} cho User ID: {} với số tiền: {}",
                request.getOrderId(), request.getUserId(), request.getAmount());

        try {
            if (request.getAmount() == 9999) {
                throw new RuntimeException("Đứt kết nối tới cơ sở dữ liệu Payment Gateway (Giả lập lỗi)");
            }

            Order order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> {
                        log.warn("[PAYMENT WARN] Thanh toán thất bại. Đơn hàng không tồn tại. OrderID: {}", request.getOrderId());
                        return new BusinessException("Đơn hàng không tồn tại trên hệ thống.");
                    });

            if (!order.getUser().getId().equals(request.getUserId())) {
                log.warn("[PAYMENT WARN] Gian lận/Sai sót thanh toán. User ID: {} cố gắng thanh toán Đơn hàng ID: {} thuộc về User ID: {}",
                        request.getUserId(), order.getId(), order.getUser().getId());
                throw new BusinessException("Bạn không có quyền thanh toán cho đơn hàng này.");
            }

            if ("PAID".equalsIgnoreCase(order.getStatus())) {
                log.warn("[PAYMENT WARN] Trùng lặp thanh toán. Đơn hàng ID: {} đã ở trạng thái PAID trước đó.", order.getId());
                throw new BusinessException("Đơn hàng này đã được thanh toán thành công trước đó.");
            }

            UserAccount account = accountRepository.findByUserId(request.getUserId())
                    .orElseThrow(() -> {
                        log.warn("[PAYMENT WARN] Tài khoản thanh toán không tồn tại cho User ID: {}", request.getUserId());
                        return new BusinessException("Tài khoản người dùng chưa kích hoạt ví thanh toán.");
                    });

            if (account.getBalance() < request.getAmount()) {
                log.warn("[PAYMENT WARN] Thanh toán thất bại do thiếu số dư. User ID: {}, Số dư hiện tại: {}, Số tiền cần trừ: {}",
                        request.getUserId(), account.getBalance(), request.getAmount());
                throw new BusinessException("Số dư tài khoản không đủ để thực hiện thanh toán.");
            }

            double oldBalance = account.getBalance();
            account.setBalance(oldBalance - request.getAmount());
            accountRepository.save(account);

            order.setStatus("PAID");
            orderRepository.save(order);

            log.info("[PAYMENT SUCCESS] Thanh toán thành công! Đơn hàng ID: {} chuyển sang trạng thái PAID. Tài khoản User ID: {} từ {}đ -> {}đ",
                    order.getId(), request.getUserId(), oldBalance, account.getBalance());

            return new PaymentResponseDTO("SUCCESS", "Thanh toán thành công.", account.getBalance());

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("[PAYMENT ERROR] SỰ CỐ HỆ THỐNG NGHIÊM TRỌNG tại luồng thanh toán Đơn hàng ID: {}. Chi tiết lỗi hệ thống: ",
                    request.getOrderId(), e);
            throw e;
        }
    }
}