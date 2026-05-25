package ra.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.BookingRequest;
import ra.model.entity.Ticket;
import ra.service.BookingService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final BookingService bookingService;

    public TicketController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<Ticket> bookTicket(@Valid @RequestBody BookingRequest request) {
        Ticket ticket = bookingService.bookTicket(request.getFlightNumber(), request.getPassengerName());
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/cancel/{ticketId}")
    public ResponseEntity<String> cancelTicket(@PathVariable Long ticketId) {
        bookingService.cancelTicket(ticketId);
        return ResponseEntity.ok("Hủy vé thành công thành công!");
    }
}