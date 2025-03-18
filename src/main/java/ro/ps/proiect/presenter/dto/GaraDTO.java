package ro.ps.proiect.presenter.dto;

import lombok.Builder;

@Builder
public record GaraDTO(Long id, String judet, String localitate, String adresaStrada) {

    @Override
    public String toString() {
        return judet + ", " + localitate;
    }
}
