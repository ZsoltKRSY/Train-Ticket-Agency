package ro.ps.proiect.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ps.proiect.model.data_structures.Tren;
import java.util.List;
import java.util.Optional;

public interface TrenRepository extends JpaRepository<Tren, Long> {

    List<Tren> findAllByNrTren(String nrTren);

    Optional<Tren> findByNrTren(String nrTren);
}
