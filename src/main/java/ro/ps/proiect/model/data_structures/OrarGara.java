package ro.ps.proiect.model.data_structures;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ORAR_GARA", uniqueConstraints = @UniqueConstraint(columnNames = {"id_tren", "id_statie", "traseu"}))
public class OrarGara {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_tren", referencedColumnName = "id")
    private Tren tren;

    @ManyToOne
    @JoinColumn(name = "id_statie", referencedColumnName = "id")
    private Gara statie;

    private LocalTime oraDePlecare;

    private LocalTime oraDeSosire;

    private Integer traseu;

}
