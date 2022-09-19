package ch.ms.coworkingspace.repository;

import ch.ms.coworkingspace.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
