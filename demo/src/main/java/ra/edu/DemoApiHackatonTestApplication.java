package ra.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class DemoApiHackatonTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApiHackatonTestApplication.class, args);
    }

}
