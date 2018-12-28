package emonitor.app.services;

import emonitor.app.database.ClientRepository;
import emonitor.app.domain.Client;
import emonitor.app.wrapper.UserWrapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClientService implements UserDetailsService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Client getUser(int id) {
        return repository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public Client getUser(String userName) {
        return repository.findByUsername(userName)
                .orElseThrow(NoSuchElementException::new);
    }

    public void save(Client client) {
        repository.save(client);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public List<Client> getAll() {
        return repository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> client = repository.findByUsername(username);
        if (!client.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return new UserWrapper(client.get());
    }
}
