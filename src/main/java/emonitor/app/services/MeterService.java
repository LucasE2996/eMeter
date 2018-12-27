package emonitor.app.services;

import emonitor.app.database.MeterRepository;
import emonitor.app.domain.Monitor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MeterService {

    private final MeterRepository repository;

    public MeterService(MeterRepository repository) {
        this.repository = repository;
    }

    public void save(Monitor monitor) {
        repository.save(monitor);
    }

    public void delete(Monitor monitor) {
        repository.delete(monitor);
    }

    public Monitor get(int id) {
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public List<Monitor> getAll(int id) {
        return repository.findAllByClientId(id);
    }
}
