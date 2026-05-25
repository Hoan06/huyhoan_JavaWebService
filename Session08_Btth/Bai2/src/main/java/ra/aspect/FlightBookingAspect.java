package ra.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import ra.model.entity.ErrorLog;
import ra.repository.ErrorLogRepository;
import ra.repository.TicketRepository;

import java.time.LocalDateTime;

@Aspect
@Component
public class FlightBookingAspect {

    private final TicketRepository ticketRepository;
    private final ErrorLogRepository errorLogRepository;

    public FlightBookingAspect(TicketRepository ticketRepository, ErrorLogRepository errorLogRepository) {
        this.ticketRepository = ticketRepository;
        this.errorLogRepository = errorLogRepository;
    }

    @Around("execution(* ra.service.BookingService.bookTicket(..))")
    public Object sanitizePassengerName(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        if (args.length >= 2 && args[1] instanceof String) {
            String rawName = (String) args[1];
            if (rawName != null) {
                String sanitizedName = rawName.trim().replaceAll("\\s+", " ").toUpperCase();
                args[1] = sanitizedName;
            }
        }

        return joinPoint.proceed(args);
    }

    @Before("execution(* ra.service.BookingService.cancelTicket(..)) && args(ticketId)")
    public void validateCancelTime(Long ticketId) {
        boolean isSatiated = ticketRepository.isFlightWithin24Hours(ticketId);
        if (isSatiated) {
            throw new RuntimeException("Không thể hủy vé! Thời gian tới giờ khởi hành còn ít hơn 24 giờ.");
        }
    }

    @AfterThrowing(pointcut = "execution(* ra.service.BookingService.*(..))", throwing = "ex")
    public void logServiceFailure(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().toShortString();
        String errorMessage = ex.getMessage();

        ErrorLog errorLog = new ErrorLog(LocalDateTime.now(), methodName, errorMessage);
        errorLogRepository.save(errorLog);
    }
}