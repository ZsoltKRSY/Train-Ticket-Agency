package ro.ps.proiect.model.data_structures;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "VAGON")
public class Vagon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String nrVagon;

    private int nrLocuriLibere;

    @ManyToOne
    @JoinColumn(name = "id_tren")
    private Tren tren;
}
