package ro.ps.proiect.presenter.dto;

import lombok.Builder;

@Builder
public record TrenDTO(Long id, String nrTren, GaraDTO garaDePlecare, GaraDTO garaDeSosire) {

    @Override
    public String toString(){
        return nrTren;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final TrenDTO otherObj = (TrenDTO)obj;

        return this.id.equals(otherObj.id) || this.nrTren.equals(otherObj.nrTren);
    }
}
