package ch.ms.coworkingspace.repository;

import ch.ms.coworkingspace.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends CrudRepository<Booking, UUID> {
    Optional<Booking> findByCreatorId(UUID id);
    List<Booking> findAllByStatus(String status);

    List<Booking> findAllByStatusAndCreatorId(String status, UUID userid);
}
