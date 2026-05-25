package ra.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.InventoryRequestDTO;
import ra.model.entity.Product;
import ra.service.IInventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final IInventoryService inventoryService;

    @PostMapping("/import")
    public ResponseEntity<String> importStock(@Valid @RequestBody InventoryRequestDTO request) {
        inventoryService.importStock(request);
        return ResponseEntity.ok("Nhập kho thành công.");
    }

    @PostMapping("/export")
    public ResponseEntity<String> exportStock(@Valid @RequestBody InventoryRequestDTO request) {
        inventoryService.exportStock(request);
        return ResponseEntity.ok("Xuất kho thành công.");
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> getLowStock() {
        return ResponseEntity.ok(inventoryService.getLowStockProducts());
    }
}