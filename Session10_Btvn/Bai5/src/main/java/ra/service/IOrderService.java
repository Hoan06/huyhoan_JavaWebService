package ra.service;

import ra.model.dto.request.CheckoutRequestDTO;

public interface IOrderService {
    void createOrder(CheckoutRequestDTO dto);
}
