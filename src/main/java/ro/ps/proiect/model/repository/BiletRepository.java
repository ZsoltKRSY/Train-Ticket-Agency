package ro.ps.proiect.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ps.proiect.model.data_structures.Bilet;

public interface BiletRepository extends JpaRepository<Bilet, Long> {
}
