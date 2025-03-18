package ro.ps.proiect.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ps.proiect.model.data_structures.Tren;
import ro.ps.proiect.model.data_structures.Vagon;
import java.util.List;

public interface VagonRepository extends JpaRepository<Vagon, Long> {

    List<Vagon> findAllByTren(Tren tren);
}
