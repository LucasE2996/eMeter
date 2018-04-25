package emonitor.app.database;

import emonitor.app.domain.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {

    List<Client> findAll();
    Client findFirstByUsername(String username);
}
