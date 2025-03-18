package ro.ps.proiect.presenter.dto.mapper;

import org.springframework.stereotype.Component;
import ro.ps.proiect.model.data_structures.Gara;
import ro.ps.proiect.presenter.dto.GaraDTO;

import java.util.List;

@Component
public class GaraMapper {
    public static GaraDTO garaEntityToDTO(Gara gara){
        return GaraDTO.builder()
                .id(gara.getId())
                .judet(gara.getJudet())
                .localitate(gara.getLocalitate())
                .adresaStrada(gara.getAdresaStrada())
                .build();
    }

    public static List<GaraDTO> garaEntityListToDTOs(List<Gara> gari){
        return gari.stream()
                .map(GaraMapper::garaEntityToDTO)
                .toList();
    }

    public static Gara garaDTOtoEntity(GaraDTO garaDTO){
        return Gara.builder()
                .id(garaDTO.id())
                .judet(garaDTO.judet())
                .localitate(garaDTO.localitate())
                .adresaStrada(garaDTO.adresaStrada())
                .build();
    }

    public static List<Gara> garaDTOListToEntities(List<GaraDTO> garaDTOs){
        return garaDTOs.stream()
                .map(GaraMapper::garaDTOtoEntity)
                .toList();
    }
}
