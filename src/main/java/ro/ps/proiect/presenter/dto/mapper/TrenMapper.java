package ro.ps.proiect.presenter.dto.mapper;

import org.springframework.stereotype.Component;
import ro.ps.proiect.model.data_structures.Tren;
import ro.ps.proiect.presenter.dto.TrenDTO;

import java.util.List;

@Component
public class TrenMapper {
    
    public static TrenDTO trenEntityToDTO(Tren tren){
        return TrenDTO.builder()
                .id(tren.getId())
                .nrTren(tren.getNrTren())
                .garaDePlecare(GaraMapper.garaEntityToDTO(tren.getGaraDePlecare()))
                .garaDeSosire(GaraMapper.garaEntityToDTO(tren.getGaraDeSosire()))
                .build();
    }

    public static List<TrenDTO> trenEntityListToDTOs(List<Tren> gari){
        return gari.stream()
                .map(TrenMapper::trenEntityToDTO)
                .toList();
    }

    public static Tren trenDTOToEntity(TrenDTO trenDTO){
        return Tren.builder()
                .id(trenDTO.id())
                .nrTren(trenDTO.nrTren())
                .garaDePlecare(GaraMapper.garaDTOtoEntity(trenDTO.garaDePlecare()))
                .garaDeSosire(GaraMapper.garaDTOtoEntity(trenDTO.garaDeSosire()))
                .build();
    }

}
