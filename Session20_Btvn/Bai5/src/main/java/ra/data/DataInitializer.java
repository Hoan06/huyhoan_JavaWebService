package ra.data;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ra.model.entity.Concert;
import ra.model.entity.FanAccount;
import ra.model.entity.TicketCategory;
import ra.repository.ConcertRepository;
import ra.repository.FanAccountRepository;
import ra.repository.TicketCategoryRepository;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final FanAccountRepository fanAccountRepository;
    private final ConcertRepository concertRepository;
    private final TicketCategoryRepository ticketCategoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        FanAccount fan = FanAccount.builder()
                .email("hoan@gmail.com")
                .password(passwordEncoder.encode("password123"))
                .fullName("Nguyễn Huy Hoàn")
                .role("FAN")
                .build();
        fanAccountRepository.save(fan);

        Concert concert1 = Concert.builder()
                .name("Sky Tour 2026")
                .showDate(LocalDate.of(2026, 8, 15))
                .location("My Dinh Stadium")
                .build();
        concertRepository.save(concert1);

        ticketCategoryRepository.save(TicketCategory.builder().concertId(concert1.getId()).name("VVIP").price(5000000.0).remainingQuantity(10).build());
        ticketCategoryRepository.save(TicketCategory.builder().concertId(concert1.getId()).name("VIP").price(3000000.0).remainingQuantity(50).build());
        ticketCategoryRepository.save(TicketCategory.builder().concertId(concert1.getId()).name("GA").price(1000000.0).remainingQuantity(0).build());
    }
}