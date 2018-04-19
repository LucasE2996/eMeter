package emonitor.app.services;

import emonitor.app.database.UserRepository;
import emonitor.app.domain.User;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getUser(int id) {
        return repository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public void save(User user) {
        repository.save(user);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
