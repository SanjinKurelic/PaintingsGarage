package eu.sanjin.kurelic.paintingsgarage.audit.repository;

import eu.sanjin.kurelic.paintingsgarage.audit.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

  List<Audit> findFirst10ByOrderByTimeDesc();
}
