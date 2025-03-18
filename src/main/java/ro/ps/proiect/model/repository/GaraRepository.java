package ro.ps.proiect.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ps.proiect.model.data_structures.Gara;

@Repository
public interface GaraRepository extends JpaRepository<Gara, Long> {
}
