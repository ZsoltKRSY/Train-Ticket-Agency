package ro.ps.proiect.presenter.dto;

import java.time.LocalTime;

public record OreDTO(LocalTime oraDePlecare, LocalTime oraDeSosire, Integer traseu, TrenDTO trenDTO) {

    @Override
    public String toString(){
        return oraDePlecare + " - " + oraDeSosire;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final OreDTO otherObj = (OreDTO)obj;

        return this.trenDTO.equals(otherObj.trenDTO) && this.traseu.equals(otherObj.traseu);
    }
}
