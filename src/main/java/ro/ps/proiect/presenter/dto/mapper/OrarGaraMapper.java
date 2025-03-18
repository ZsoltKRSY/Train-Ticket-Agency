package ro.ps.proiect.presenter.dto.mapper;

import org.springframework.stereotype.Component;
import ro.ps.proiect.model.data_structures.OrarGara;
import ro.ps.proiect.presenter.dto.OrarGaraDTO;

import java.util.List;

@Component
public class OrarGaraMapper {

    public static OrarGaraDTO orarGaraEntityToDTO(OrarGara orarGara){
        return OrarGaraDTO.builder()
                .id(orarGara.getId())
                .garaDTO(GaraMapper.garaEntityToDTO(orarGara.getStatie()))
                .oraDeSosire(orarGara.getOraDeSosire())
                .oraDePlecare(orarGara.getOraDePlecare())
                .traseu(orarGara.getTraseu())
                .build();
    }

    public static List<OrarGaraDTO> orarGaraEntityListToDTOs(List<OrarGara> gari){
        return gari.stream()
                .map(OrarGaraMapper::orarGaraEntityToDTO)
                .toList();
    }
    
}
