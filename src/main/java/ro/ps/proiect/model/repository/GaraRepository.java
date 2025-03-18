package ro.ps.proiect.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ps.proiect.model.data_structures.Gara;

public interface GaraRepository extends JpaRepository<Gara, Long> {
}
