package emonitor.app.services;

import emonitor.app.database.ClientRepository;
import emonitor.app.domain.Client;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Client getUser(int id) {
        return repository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public void save(Client client) {
        repository.save(client);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
