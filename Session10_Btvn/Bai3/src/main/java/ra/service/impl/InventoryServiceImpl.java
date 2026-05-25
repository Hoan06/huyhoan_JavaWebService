package ra.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.exception.InventoryException;
import ra.model.dto.request.InventoryRequestDTO;
import ra.model.entity.Product;
import ra.repository.ProductRepository;
import ra.repository.WarehouseKeeperRepository;
import ra.service.IInventoryService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements IInventoryService {

    private final ProductRepository productRepository;
    private final WarehouseKeeperRepository keeperRepository;

    @Override
    @Transactional
    public void importStock(InventoryRequestDTO request) {
        if (!keeperRepository.existsById(request.getKeeperId())) {
            log.warn("[INVENTORY WARN] Nhập kho thất bại. Không tìm thấy nhân viên ID: {}", request.getKeeperId());
            throw new InventoryException("Nhân viên kho không tồn tại trên hệ thống.");
        }

        Product product = productRepository.findBySku(request.getSku())
                .orElseThrow(() -> {
                    log.warn("[INVENTORY WARN] Nhập kho thất bại. Không tìm thấy SKU: {}", request.getSku());
                    return new InventoryException("Mã SKU sản phẩm không tồn tại.");
                });

        productRepository.importProductDirectly(request.getSku(), request.getQuantity());
        log.info("[INVENTORY INFO] Nhân viên {} đã NHẬP thêm {} sản phẩm mã SKU: {}",
                request.getKeeperId(), request.getQuantity(), request.getSku());
    }

    @Override
    @Transactional
    public void exportStock(InventoryRequestDTO request) {
        if (!keeperRepository.existsById(request.getKeeperId())) {
            log.warn("[INVENTORY WARN] Xuất kho thất bại. Không tìm thấy nhân viên ID: {}", request.getKeeperId());
            throw new InventoryException("Nhân viên kho không tồn tại.");
        }

        Product product = productRepository.findBySku(request.getSku())
                .orElseThrow(() -> {
                    log.warn("[INVENTORY WARN] Xuất kho thất bại. Không tìm thấy SKU: {}", request.getSku());
                    return new InventoryException("Mã SKU sản phẩm không tồn tại.");
                });

        if (product.getQuantity() < request.getQuantity()) {
            log.warn("[INVENTORY WARN] Xuất lố hàng! SKU: {} hiện có {} sản phẩm, nhưng yêu cầu xuất {}",
                    request.getSku(), product.getQuantity(), request.getQuantity());
            throw new IllegalArgumentException("Số lượng xuất hàng vượt quá tồn kho hiện tại!");
        }

        productRepository.exportProductDirectly(request.getSku(), request.getQuantity());
        log.info("[INVENTORY INFO] Nhân viên {} đã XUẤT {} sản phẩm mã SKU: {}",
                request.getKeeperId(), request.getQuantity(), request.getSku());

        if ("SLOW-PROD".equalsIgnoreCase(request.getSku())) {
            try { Thread.sleep(600); } catch (InterruptedException ignored) {}
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getLowStockProducts() {
        return productRepository.findByQuantityLessThan(5L);
    }
}