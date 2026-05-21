package ra.controller;

import org.springframework.web.bind.annotation.*;
import ra.service.TransactionService;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/send")
    public String transferCrypto(@RequestParam String wallet, @RequestParam double amount) {
        return transactionService.performTransaction(wallet, amount);
    }
}