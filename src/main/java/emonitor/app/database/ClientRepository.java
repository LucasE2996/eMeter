package emonitor.app.database;

import emonitor.app.domain.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {

    List<Client> findAll();
    Optional<Client> findFirstByUsername(String username);
}
