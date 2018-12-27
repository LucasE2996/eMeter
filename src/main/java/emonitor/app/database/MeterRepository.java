package emonitor.app.database;

import emonitor.app.domain.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterRepository extends JpaRepository<Monitor, Integer> {

    Monitor findByChannel(Integer channel);
    List<Monitor> findAllByClientId(Integer id);
}
