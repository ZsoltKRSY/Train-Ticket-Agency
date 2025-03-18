package ro.ps.proiect.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ps.proiect.model.data_structures.Bilet;
import ro.ps.proiect.model.data_structures.Gara;
import ro.ps.proiect.model.data_structures.Tren;

import java.time.LocalDate;
import java.util.List;

public interface BiletRepository extends JpaRepository<Bilet, Long> {

    List<Bilet> findAllByDataCalatoriei(LocalDate dataCalatoriei);

    List<Bilet> findAllByGaraDeDestinatie(Gara garaDeDestinatie);

    List<Bilet> findAllByVagonTren(Tren tren);
}
