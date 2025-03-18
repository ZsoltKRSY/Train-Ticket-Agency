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

    public static List<BiletDTO> biletEntityListToDTOs(List<Bilet> bilete){
        return bilete.stream()
                .map(BiletMapper::biletEntityToDTO)
                .toList();
    }

    public static Bilet biletDTOToEntity(BiletDTO biletDTO){
        return Bilet.builder()
                .id(biletDTO.id())
                .dataCalatoriei(biletDTO.dataCalatoriei())
                .garaDePlecare(GaraMapper.garaDTOtoEntity(biletDTO.garaDePlecare()))
                .garaDeDestinatie(GaraMapper.garaDTOtoEntity(biletDTO.garaDeDestinatie()))
                .oraDePlecare(biletDTO.oraDePlecare())
                .oraDeSosire(biletDTO.oraDeSosire())
                .vagon(VagonMapper.vagonDTOToEntity(biletDTO.vagon()))
                .nrLoc(biletDTO.nrLoc())
                .build();
    }

    public static List<Bilet> biletDTOListToEntities(List<BiletDTO> biletDTOs){
        return biletDTOs.stream()
                .map(BiletMapper::biletDTOToEntity)
                .toList();
    }
}
