package ro.ps.proiect.model.data_structures;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "BILET")
public class Bilet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private LocalDate dataCalatoriei;

    @ManyToOne
    @JoinColumn(name = "id_garaDePlecare", referencedColumnName = "id")
    private Gara garaDePlecare;

    @ManyToOne
    @JoinColumn(name = "id_garaDeDestinatie", referencedColumnName = "id")
    private Gara garaDeDestinatie;

    private LocalTime oraDePlecare;

    private LocalTime oraDeSosire;

    @ManyToOne
    @JoinColumn(name = "id_vagon", referencedColumnName = "id")
    private Vagon vagon;

    private int nrLoc;
}
