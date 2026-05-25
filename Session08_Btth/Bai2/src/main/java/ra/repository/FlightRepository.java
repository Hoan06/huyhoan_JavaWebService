package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Flight;

import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByFlightNumber(String flightNumber);
}