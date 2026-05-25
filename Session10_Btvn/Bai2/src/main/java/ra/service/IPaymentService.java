package ra.service;


import ra.model.dto.request.PaymentRequestDTO;
import ra.model.dto.response.PaymentResponseDTO;

public interface IPaymentService {
    PaymentResponseDTO processPayment(PaymentRequestDTO request);
}