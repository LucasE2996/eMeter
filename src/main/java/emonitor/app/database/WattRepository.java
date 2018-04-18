package emonitor.app.database;

import emonitor.app.domain.Watt;
import org.springframework.data.repository.CrudRepository;

public interface WattRepository extends CrudRepository<Watt, Integer> {
}
