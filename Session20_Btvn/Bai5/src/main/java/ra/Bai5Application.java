package ra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "ra")
public class Bai5Application {

    public static void main(String[] args) {
        SpringApplication.run(Bai5Application.class, args);
    }

}
