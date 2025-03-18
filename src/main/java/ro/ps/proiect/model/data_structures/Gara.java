package ro.ps.proiect.model.data_structures;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "GARA")
public class Gara {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String judet;

    private String localitate;

    private String adresaStrada;

    @Override
    public String toString(){
        return judet + ", " + localitate;
    }

}
