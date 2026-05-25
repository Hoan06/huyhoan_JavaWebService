package ra.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ra.service.IInventoryService;

@Service
@Slf4j
public class InventoryServiceImpl implements IInventoryService {
    @Override
    public void updateInventory(String orderCode) {
        log.info("[InventoryService] Tiến hành đồng bộ, giảm số lượng hàng hóa trong kho.");

        throw new RuntimeException("Inventory Update Failed");
    }
}