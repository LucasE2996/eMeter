package emonitor.app.database;

import emonitor.app.domain.Watt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WattRepository extends CrudRepository<Watt, Integer> {
}
