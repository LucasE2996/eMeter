package emonitor.app.database;

import emonitor.app.domain.Meter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeterRepository extends CrudRepository<Meter, Integer> {

    Meter findByChannel(Integer channel);
    List<Meter> findAllByClientId(Integer id);
}
