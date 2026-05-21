package ra.service.impl;

import org.springframework.stereotype.Service;
import ra.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Override
    public String method1() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Kết quả thực thi phương thức 1";
    }

    @Override
    public String method2() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Kết quả thực thi phương thức 2";
    }
}
