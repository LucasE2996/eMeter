package emonitor.app.database;

import emonitor.app.domain.Meter;
import org.springframework.data.repository.CrudRepository;

public interface MeterRepository extends CrudRepository<Meter, Integer> {
}
