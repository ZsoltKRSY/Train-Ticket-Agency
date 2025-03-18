package ro.ps.proiect.presenter.dto.mapper;

import org.springframework.stereotype.Component;
import ro.ps.proiect.model.data_structures.Bilet;
import ro.ps.proiect.presenter.dto.BiletDTO;

import java.util.List;

@Component
public class BiletMapper {

    public static BiletDTO biletEntityToDTO(Bilet bilet){
        return BiletDTO.builder()
                .id(bilet.getId())
                .dataCalatoriei(bilet.getDataCalatoriei())
                .garaDePlecare(GaraMapper.garaEntityToDTO(bilet.getGaraDePlecare()))
                .garaDeDestinatie(GaraMapper.garaEntityToDTO(bilet.getGaraDeDestinatie()))
                .oraDePlecare(bilet.getOraDePlecare())
                .oraDeSosire(bilet.getOraDeSosire())
                .tren(TrenMapper.trenEntityToDTO(bilet.getVagon().getTren()))
                .vagon(VagonMapper.vagonEntityToDTO(bilet.getVagon()))
                .nrLoc(bilet.getNrLoc())
                .build();
    }

    public static List<BiletDTO> biletEntityListToDTOs(List<Bilet> gari){
        return gari.stream()
                .map(BiletMapper::biletEntityToDTO)
                .toList();
    }
}
