package ro.ps.proiect.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ps.proiect.model.data_structures.Gara;
import ro.ps.proiect.model.data_structures.OrarGara;
import ro.ps.proiect.model.data_structures.Tren;
import java.util.List;

public interface OrarGaraRepository extends JpaRepository<OrarGara, Long> {

    List<OrarGara> findAllByTren(Tren tren);

    List<OrarGara> findAllByStatie(Gara gara);
}
