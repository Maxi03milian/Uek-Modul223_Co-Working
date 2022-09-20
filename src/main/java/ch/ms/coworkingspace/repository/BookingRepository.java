package ch.ms.coworkingspace.repository;

import ch.ms.coworkingspace.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {
    Optional<Booking> findByCreatorId(Long id);
    List<Booking> findAllByStatus(String status);
}
