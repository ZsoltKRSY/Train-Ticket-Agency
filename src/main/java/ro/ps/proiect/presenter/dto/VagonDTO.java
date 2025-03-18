package ro.ps.proiect.presenter.dto;

import lombok.Builder;

@Builder
public record VagonDTO(Long id, String nrVagon, TrenDTO trenDTO, int nrLocuriLibere) {

    @Override
    public String toString(){
        return nrVagon;
    }
}
