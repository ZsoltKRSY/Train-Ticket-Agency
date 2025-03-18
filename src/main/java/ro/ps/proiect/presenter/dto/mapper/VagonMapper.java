package ro.ps.proiect.presenter.dto.mapper;

import org.springframework.stereotype.Component;
import ro.ps.proiect.model.data_structures.Vagon;
import ro.ps.proiect.presenter.dto.VagonDTO;

import java.util.List;

@Component
public class VagonMapper {

    public static VagonDTO vagonEntityToDTO(Vagon vagon){
        return VagonDTO.builder()
                .id(vagon.getId())
                .nrVagon(vagon.getNrVagon())
                .trenDTO(TrenMapper.trenEntityToDTO(vagon.getTren()))
                .nrLocuriLibere(vagon.getNrLocuriLibere())
                .build();
    }

    public static List<VagonDTO> vagonEntityListToDTOs(List<Vagon> gari){
        return gari.stream()
                .map(VagonMapper::vagonEntityToDTO)
                .toList();
    }

    public static Vagon vagonDTOToEntity(VagonDTO vagonDTO){
        return Vagon.builder()
                .id(vagonDTO.id())
                .nrVagon(vagonDTO.nrVagon())
                .tren(TrenMapper.trenDTOToEntity(vagonDTO.trenDTO()))
                .nrLocuriLibere(vagonDTO.nrLocuriLibere())
                .build();
    }
    
}
