package ra.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ra.model.dto.request.CheckoutRequestDTO;
import ra.service.IOrderService;

@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {
    @Override
    public void createOrder(CheckoutRequestDTO dto) {
        log.info("[OrderService] Khởi tạo đơn hàng '{}' ở trạng thái PENDING.", dto.getOrderCode());
    }
}
