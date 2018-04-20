package emonitor.app.database;

import emonitor.app.domain.Meter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterRepository extends CrudRepository<Meter, Integer> {
}
