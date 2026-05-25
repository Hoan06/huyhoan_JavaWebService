package ra.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.custom_valid.RequireManagerApproval;
import ra.custom_valid.RequireOtp;
import ra.model.dto.request.PaymentRequest;
import ra.model.dto.request.RefundRequest;
import ra.model.entity.Transaction;
import ra.repository.TransactionRepository;
import java.util.UUID;

@Service
public class PaymentService {

    private final TransactionRepository transactionRepository;

    public PaymentService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction processDomesticPayment(PaymentRequest request) {
        Transaction tx = new Transaction();
        tx.setTransactionCode("TXN" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        tx.setAmount(request.getAmount());
        tx.setCurrency(request.getCurrency());
        tx.setType("DOMESTIC");
        return transactionRepository.save(tx);
    }

    @RequireOtp
    @Transactional
    public Transaction processInternationalPayment(PaymentRequest request) {
        Transaction tx = new Transaction();
        tx.setTransactionCode("TXN" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        tx.setAmount(request.getAmount());
        tx.setCurrency(request.getCurrency());
        tx.setType("INTERNATIONAL");
        return transactionRepository.save(tx);
    }

    @RequireManagerApproval
    @Transactional
    public Transaction processRefund(RefundRequest request) {
        Transaction originalTx = transactionRepository.findByTransactionCode(request.getTransactionCode())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giao dịch gốc"));

        if (originalTx.getAmount() < request.getAmount()) {
            throw new RuntimeException("Số tiền hoàn trả vượt quá số tiền giao dịch gốc");
        }

        Transaction refundTx = new Transaction();
        refundTx.setTransactionCode("REF" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        refundTx.setAmount(request.getAmount());
        refundTx.setCurrency(originalTx.getCurrency());
        refundTx.setType("REFUND");
        return transactionRepository.save(refundTx);
    }
}