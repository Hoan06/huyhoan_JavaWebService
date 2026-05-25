package ra.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.model.entity.Flight;
import ra.model.entity.Ticket;
import ra.repository.FlightRepository;
import ra.repository.TicketRepository;

@Service
public class BookingService {

    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;

    public BookingService(FlightRepository flightRepository, TicketRepository ticketRepository) {
        this.flightRepository = flightRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public Ticket bookTicket(String flightNumber, String passengerName) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new RuntimeException("Chuyến bay không tồn tại: " + flightNumber));

        if (flight.getAvailableSeats() <= 0) {
            throw new RuntimeException("Chuyến bay đã hết vé!");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        Ticket ticket = new Ticket();
        ticket.setPassengerName(passengerName);
        ticket.setFlightId(flight.getId());
        ticket.setStatus("BOOKED");

        return ticketRepository.save(ticket);
    }

    @Transactional
    public void cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé có ID: " + ticketId));

        if ("CANCELED".equals(ticket.getStatus())) {
            throw new RuntimeException("Vé này đã được hủy từ trước!");
        }

        Flight flight = flightRepository.findById(ticket.getFlightId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến bay liên quan đến vé"));
        flight.setAvailableSeats(flight.getAvailableSeats() + 1);
        flightRepository.save(flight);

        ticket.setStatus("CANCELED");
        ticketRepository.save(ticket);
    }
}