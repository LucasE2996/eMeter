package emonitor.app.services;

import emonitor.app.database.MeterRepository;
import emonitor.app.domain.Meter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MeterService {

    private final MeterRepository repository;

    public MeterService(MeterRepository repository) {
        this.repository = repository;
    }

    public void save(Meter meter) {
        repository.save(meter);
    }

    public void delete(Meter meter) {
        repository.delete(meter);
    }

    public Meter get(int id) {
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public List<Meter> getAll(int id) {
        return repository.findAllByClientId(id);
    }
}
