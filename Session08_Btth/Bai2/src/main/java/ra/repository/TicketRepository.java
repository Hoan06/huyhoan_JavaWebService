package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ra.model.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT CASE WHEN FUNCTION('TIMESTAMPDIFF', 'HOUR', CURRENT_TIMESTAMP, f.departureTime) < 24 THEN true ELSE false END " +
            "FROM Ticket t JOIN Flight f ON t.flightId = f.id WHERE t.id = :ticketId")
    boolean isFlightWithin24Hours(@Param("ticketId") Long ticketId);
}