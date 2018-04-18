package emonitor.app.database;

import emonitor.app.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<User, Integer> {
}
