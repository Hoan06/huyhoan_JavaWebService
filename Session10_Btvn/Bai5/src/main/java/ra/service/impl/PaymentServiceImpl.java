package ra.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ra.model.dto.request.CheckoutRequestDTO;
import ra.service.IPaymentService;

@Service
@Slf4j
public class PaymentServiceImpl implements IPaymentService {
    @Override
    public void processPayment(CheckoutRequestDTO dto) {
        log.info("[PaymentService] Đang yêu cầu xử lý trừ số tiền: {}đ", dto.getAmount());
    }
}