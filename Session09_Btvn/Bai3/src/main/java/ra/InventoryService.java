package ra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InventoryService {

    public void updateStock(String productId, int qty) {

        log.info("Bắt đầu cập nhật kho cho SP: {}, SL: {}", productId, qty);

        boolean isSuccess = true;

        if (isSuccess) {
            log.info("Cập nhật kho thành công cho sản phẩm '{}'. Số lượng mới trong kho tăng thêm {} đơn vị.", productId, qty);
        }
    }
}