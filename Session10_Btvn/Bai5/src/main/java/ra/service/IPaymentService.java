package ra.service;

import ra.model.dto.request.CheckoutRequestDTO;

public interface IPaymentService {
    void processPayment(CheckoutRequestDTO dto);
}
