package ro.ps.proiect.model.data_structures;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TREN")
public class Tren {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String nrTren;

    @ManyToOne
    @JoinColumn(name = "id_garaDePlecare", referencedColumnName = "id")
    private Gara garaDePlecare;

    @ManyToOne
    @JoinColumn(name = "id_garaDeSosire", referencedColumnName = "id")
    private Gara garaDeSosire;

}
