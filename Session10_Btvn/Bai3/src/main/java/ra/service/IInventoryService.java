package ra.service;


import ra.model.dto.request.InventoryRequestDTO;
import ra.model.entity.Product;

import java.util.List;

public interface IInventoryService {
    void importStock(InventoryRequestDTO request);
    void exportStock(InventoryRequestDTO request);
    List<Product> getLowStockProducts();
}